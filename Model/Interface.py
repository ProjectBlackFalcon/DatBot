import ast
from threading import Thread
import sys
from queue import Queue, Empty
from subprocess import Popen, PIPE
import time
import datetime
import traceback


class PipeToJava:
    def __init__(self, headless=True):
        self.buffer = []
        on_posix = 'posix' in sys.builtin_module_names
        args = ['java', '-jar', '..//BotTest.jar']
        if not headless:
            args.append("true")

        self.p = Popen(args, stdin=PIPE, stdout=PIPE, bufsize=4096, close_fds=on_posix)
        self.q = Queue()
        self.t = Thread(target=self.enqueue_output, args=(self.p.stdout, self.q))
        self.t.daemon = True  # thread dies with the program
        self.t.start()

    def enqueue_output(self, out, queue):
        for line in iter(out.readline, b''):
            queue.put(line)
        out.close()

    def get_buffer(self):
        local_buffer = []
        try:
            while 1:
                read = self.q.get_nowait()
                local_buffer.append(read.strip().decode('latin-1'))
        except Empty:
            pass

        for message in local_buffer:
            if len(message.split(';')) > 5:  # Shitty attempt to differentiate actual messages and debug prints
                self.buffer.append(message)
        return self.buffer

    def remove_from_buffer(self, bot_id, message_id):
        new_buffer = []
        for entry in self.buffer:
            if not ('{};{}'.format(bot_id, message_id) in entry):
                new_buffer.append(entry)
        self.buffer = new_buffer[:]


class Interface:
    def __init__(self, bot, color=''):
        self.bot = bot  # type: Bot
        self.pipe = self.bot.pipe  # type: PipeToJava
        self.current_id = 0
        self.bank_info = {}
        self.color = color
        self.end_color = '\033[0m'

    def add_command(self, command, parameters=None):
        # <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
        message = '{};{};i;cmd;{};{}\r\n'.format(self.bot.id, self.current_id, command, parameters)
        print(self.color + '[Interface {}] Sending : '.format(self.bot.id), message.strip() + self.end_color)
        self.current_id += 1
        self.pipe.p.stdin.write(bytes(message, 'utf-8'))
        self.pipe.p.stdin.flush()
        return self.current_id-1

    def wait_for_return(self, message_id, timeout=600):
        # print('[Interface] Waiting for response...')
        ret_val = None
        message_queue = []
        start = time.time()
        while ret_val is None and time.time()-start < timeout:
            partial_message = '{};{};m;rtn'.format(self.bot.id, message_id)
            buffer = self.pipe.get_buffer()
            for message in buffer:
                if int(message.split(';')[0]) == self.bot.id and message not in message_queue:
                    print(self.color + '[Interface {}] Recieved : '.format(self.bot.id) + message + self.end_color)
                    message_queue.append(message)

            for message in message_queue:
                if partial_message in message and not self.bot.in_fight:
                    ret_val = ast.literal_eval(message.split(';')[-1])
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                elif 'info;combat;["start"]' in message:
                    print(self.color + 'Fight Started' + self.end_color)
                    self.bot.in_fight = True
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                elif 'info;combat;["end"]' in message:
                    print(self.color + 'Fight Ended' + self.end_color)
                    self.bot.in_fight = False
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                elif 'info;disconnect;[True]' in message:
                    print(self.color + '[Interface {}] Disconnected'.format(self.bot.id) + self.end_color)
                    self.bot.connected = False
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                    self.connect()

            time.sleep(0.1)
        if not self.bot.in_fight and ret_val is not None:
            # print('[Interface] Recieved : ', ret_val)
            return tuple(ret_val)
        else:
            print('[Interface] Request timed out')
            raise Exception('Request timed out')

    def execute_command(self, command, parameters=None):
        """
        Executes interface commands, logs errors
        :param command: command to send
        :param parameters: params for the command
        :return: return value form interface
        """
        try:
            msg_id = self.add_command(command, parameters)
            return self.wait_for_return(msg_id)
        except Exception as e:
            with open('..//Utils//InterfaceErrors.txt', 'a') as f:
                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                f.write(traceback.format_exc())

    def connect(self):
        """
        Connects a bot instance
        :return: Boolean/['Save']
        """
        connection_param = [
            self.bot.credentials['username'],
            self.bot.credentials['password'],
            self.bot.credentials['name'],
            self.bot.credentials['server']
        ]
        success = [True]
        if not self.bot.connected:
            success = self.execute_command('connect', connection_param)
            self.bot.connected = success[0]
            if self.bot.connected == 'Save':
                while self.bot.connected == 'Save':
                    time.sleep(60*5)
                    success = self.execute_command('connect', connection_param)
                    self.bot.connected = success[0]
            if self.bot.connected:
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                bot_stats = self.bot.interface.get_player_stats()
                bot_stats = bot_stats[0]
                self.bot.kamas = bot_stats['Inventory']['Kamas']
                self.bot.level = bot_stats['Lvl']
                self.bot.position = (current_map, current_worldmap)
        return success

    def disconnect(self):
        """
        Disconnects the bot instance
        :return:
        """
        success = [True]
        if self.bot.connected:
            success = self.execute_command('disconnect')
            if success[0]:
                self.bot.connected = False
        return success

    def get_map(self):
        """
        Gets the map the player is on
        :return: coords, cell, worldmap, mapID
        """
        current_map, current_cell, current_worldmap, map_id = self.execute_command('getMap')
        self.bot.position = (current_map, current_worldmap)
        return current_map, current_cell, current_worldmap, map_id

    def move(self, cell):
        """
        Moves the bot on a map
        :param cell: target cell number
        :return: Boolean
        """
        return self.execute_command('move', [cell])

    def change_map(self, cell, direction):
        """
        Moves the bot to an adjacent map
        :param cell: target cell number for map change
        :param direction: cardnial direction as 'n', 's', 'w', 'e'
        :return: Boolean
        """
        return self.execute_command('changeMap', [cell, direction])

    def get_map_resources(self):
        """
        Gets the resources and their info for the map the player is on
        :return: TODO
        """
        return self.execute_command('getResources')

    def get_player_stats(self):
        """
        Get the bot player stats
        :return: {"Weight": <>, "WeightMax": <>, "Lvl": <>, "Job": {"job_id": level, ...}}
        """
        stats = self.execute_command('getStats')
        time.sleep(0.5)
        print(self.bot.id, stats)
        self.bot.kamas = stats[0]['Inventory']['Kamas']
        self.bot.level = stats[0]['Lvl']
        return stats

    def harvest_resource(self, cell):
        """
        Harvests the resource on the cell given
        :param cell: cell number
        :return: [id, number_harvested, new_pods, max_pods], or combat or false
        """
        return self.execute_command('harvest', [cell])

    def go_to_astrub(self):
        """
        Talks to the PNJ to go to Astrub
        :return: Boolean
        """
        return self.execute_command('goAstrub')

    def go_to_incarnam(self):
        """
        Uses a statue to go to Incarnam
        :return: Boolean
        """
        return self.execute_command('goIncarnam')

    def get_class_statue_cell(self):
        """
        Returns the cell id of the current map class statue, or False if there is none
        :return: [cell] or [False]
        """
        return self.execute_command('getStatue')

    def get_bank_door_cell(self):
        """
        Returns the cell id of the current map bank door, or False if there is none
        :return: [cell_in, cell_out] or [False]
        """
        return self.execute_command('getBankDoor')

    def enter_bank(self):
        """
        Uses a door to enter bank
        :return: Boolean
        """
        return self.execute_command('goBank')

    def open_bank(self):
        """
        Opens bank
        :return: items json / False
        """
        bank_content = self.execute_command('openBank')
        self.bank_info = bank_content[0]
        return bank_content

    def close_bank(self):
        """
        Closes Bank
        :return: Boolean
        """
        self.bank_info = {}
        return self.execute_command('closeBank')

    def drop_in_bank_list(self, item_id_list):
        """
        Drops some items in bank
        :param item_id_list: [ItemID1, ItemID2...] / ['all']
        :return: New bank content, new inventory content
        """
        if item_id_list in ['All', 'all']:
            inventory_content, bank_content = self.execute_command('dropBankAll')
        else:
            inventory_content, bank_content = self.execute_command('dropBankList', item_id_list)
        self.bank_info = bank_content
        return inventory_content, bank_content

    def drop_in_bank_unique(self, item_id, quantity):
        """
        Drops a certain quantity of a certain item in inventory
        :param item_id: Item unique inventory id
        :param quantity: quantity of item to drop
        :return: New bank content, new inventory content
        """
        inventory_content, bank_content = self.execute_command('dropBank', [item_id, quantity])
        self.bank_info = bank_content
        return inventory_content, bank_content

    def get_from_bank_list(self, item_id_list):
        """
        Retrieves some items in bank
        :param item_id_list: [ItemID1, ItemID2...] / ['All']
        :return: New bank content, new inventory content
        """
        inventory_content, bank_content = self.execute_command('getBankList', item_id_list)
        self.bank_info = bank_content
        return inventory_content, bank_content

    def get_from_bank_unique(self, item_id, quantity):
        """
        Retrieves a certain quantity of a certain item in bank
        :param item_id: Item unique inventory id
        :param quantity: quantity of item to retrieve
        :return: New bank content, new inventory content
        """
        inventory_content, bank_content = self.execute_command('getBank', [item_id, quantity])
        self.bank_info = bank_content
        return inventory_content, bank_content

    def put_kamas_in_bank(self, quantity):
        """
        Drops a specified quantity of kamas in bank
        :param quantity: quantity of kamas to drop
        :return: New bank content, new inventory content
        """
        kamas = self.get_player_stats()[0]['Kamas']
        if quantity in ['all', 'All'] or quantity > kamas:
            quantity = kamas
        inventory_content, bank_content = self.execute_command('dropBankKamas', [quantity])
        self.bank_info = bank_content
        return inventory_content, bank_content

    def get_kamas_from_bank(self, quantity):
        """
        Retrieves a specified quantity of kamas from bank
        :param quantity: quantity of kamas to drop
        :return: New bank content, new inventory content
        """
        print(self.bank_info)
        kamas = self.bank_info['Kamas']
        if quantity in ['all', 'All'] or quantity > kamas:
            quantity = kamas
        if quantity:
            inventory_content, bank_content = self.execute_command('getBankKamas', [quantity])
            self.bank_info = bank_content
            return inventory_content, bank_content

    def get_hunting_hall_door_cell(self):
        """
        Returns the cell id of the hunting hall door, or False if there is none
        :return: [cell_in, cell_out] or [False]
        """
        return self.execute_command('getHuntingHallDoorCell')

    def enter_hunting_hall(self):
        """
        Uses the door to enter the hunting hall, then moves to the main room
        :return: Boolean
        """
        return self.execute_command('goHuntingHall')

    def exit_hunting_hall(self):
        """
        Goes back the the first room, and exits the hall
        :return: Boolean
        """
        return self.execute_command('exitHuntingHall')

    def get_new_hunt(self, level='max'):
        """
        The bot gets a new hunt at the tresure hunt thing. It should already be standing at the right spot
        :param level:
        :return: Boolean
        """
        if level == 'max':
            return self.execute_command('newHunt')
        else:
            return self.execute_command('newHunt', [level])

    def hunt_is_active(self):
        """
        Checks wether a hunt is active or not
        :return: Boolean
        """
        return self.execute_command('huntActive')

    def abandon_hunt(self):
        """
        The bot drops the hunt
        :return: Boolean
        """
        return self.execute_command('abandonHunt')

    def get_hunt_start(self):
        """
        Returns the starting pos of the hunt
        :return:
        """
        return self.execute_command('getHuntStart')

    def get_clues_left(self):
        """
        Gets the number of clues left in this step
        :return:
        """
        return self.execute_command('getCluesLeft')

    def get_steps_left(self):
        """
        Gets the number of steps left in the hunt
        :return:
        """
        return self.execute_command('getStepsLeft')

    def get_hunt_clue(self):
        """
        Returns the clue the bot should be looking for
        :return: [clue : String, direction : 'n','s','w','e']
        """
        return self.execute_command('getClue')

    def validate_hunt_clue(self):
        """
        Validates the clue (the little flag in the gui)
        :return: boolean
        """
        return self.execute_command('validateClue')

    def check_for_phorror(self):
        """
        If a phorror is on the map, return it's name
        :return: String or False
        """
        return self.execute_command('checkPhorror')

    def validate_hunt_step(self):
        """
        Validates the full step.
        :return: Boolean
        """
        return self.execute_command('validateStep')

    def start_hunt_fight(self):
        """
        Starts the fight
        :return: Boolean
        """
        return self.execute_command('huntFight')

    def enter_heavenbag(self):
        """
        Enters heavenbag
        :return: Boolean
        """
        return self.execute_command('enterBag')

    def exit_heavenbag(self):
        """
        Exits heavenbag
        :return: Boolean
        """
        return self.execute_command('exitBag')

    def get_zaap_cell(self):
        """
        Returns the cell id of the current map zaap, or False if there is none
        :return: [cell] or [False]
        """
        return self.execute_command('getZaap')

    def use_zaap(self, destination):
        """
        Uses the zaap to go to destination
        :param destination: coords (ex: (-2, 0))
        :return: Boolean
        """
        destination = ast.literal_eval(str(destination).replace('[', '(').replace(']', ')'))
        return self.execute_command('useZaap', [destination])

    def get_monsters(self):
        """
        Gives the positions and types of monsters on the map
        :return: [[mob_id, mob_name_ref, cell], [...]]
        """
        return self.execute_command('getMonsters')

    def attack_monster(self, mob_id):
        """
        Attacks the mob specified my mob_id. The bot must be on the same cell.
        :param mob_id: Id of the mob to attack. Given by get_monsters.
        :return:
        """
        return self.execute_command('attackMonster', [mob_id])

    def open_hdv(self):
        """
        Tries to open the map's hdv. If sucessful, returns what items are being sold.
        :return: False / "empty" / [[name, id, batch_size, price], [...]]
        """
        return self.execute_command('openHdv')

    def close_hdv(self):
        """
        Closes the hdv
        :return: Boolean
        """
        return self.execute_command('closeHdv')

    def get_hdv_item_stats(self, item_id):
        """
        Gathers data about the item given
        :param item_id: item id
        :return: False / [price1, price 10, price 100]
        """
        return self.execute_command('getHdvItemStats', [item_id])

    def sell_item(self, item_id, batch_size, batch_number, price):
        """
        Sells an item as a batch
        :param item_id: Item id
        :param batch_size: 1, 10, or 100 items
        :param batch_number: Number of batches to sell
        :return: Boolaen
        """
        return self.execute_command('sellItem', [item_id, batch_size, batch_number, price])

    def modify_price(self, item_id, batch_size, new_price):
        """
        Modifies the selling price of an item
        :param item_id: item id
        :param batch_size: which batch size to modify (1, 10, 100)
        :param new_price: New price
        :return: Boolean
        """
        return self.execute_command('modifyPrice', [item_id, batch_size, new_price])

    def withdraw_item(self, item_id, batch_size, batch_number):
        """
        Cancels a selling order
        :param item_id: item ID
        :param batch_size: which batch size to withdraw (1, 10, 100)
        :param batch_number: Number of batches to withdraw
        :return: Boolean
        """
        return self.execute_command('withdrawItem', [item_id, batch_size, batch_number])

    def enter_bwork(self):
        """
        Bot is already on the right cell, just use the activable to enter bwork village
        :return:
        """
        return self.execute_command('enterBwork')

    def exit_bwork(self):
        """
        Bot is already on the right cell, just use the activable to exit bwork village
        :return:
        """
        return self.execute_command('exitBwork')

    def get_dd_pen_door(self):
        """
        Gives the interactive cell
        :return: [False] / [cellId]
        """
        return self.execute_command('getDDPenDoor')

    def open_dd(self):
        """
        Opens the DD pen and returns contents. The bot is already on the right spot
        :return: [False] / [{
                              "id": 99999,
                              "behaviours": ["thing1", "thing2"],
                              "name": "name",
                              "sex": True,
                              "experience": 10000,
                              "level": 5,
                              "stamina": 10000,
                              "staminaMax": 10000,
                              "maturity": 10000,
                              "maturityForAdult": 10000,
                              "energy": 7400,
                              "energyMax": 7400,
                              "serenity": 0,
                              "serenityMax": 10000,
                              "aggressivityMax": -10000,
                              "love": 10000,
                              "loveMax": 10000,
                              "fecondationTime": -1,
                              "isFecondationReady": True,
                              "boostLimiter": 0,
                              "boostMax": 240,
                              "reproductionCount": 6,
                              "reproductionCountMax" : 20,
                              "inPaddock"; True
                        },
                        {}, ...]
        """
        return self.execute_command('openDD')

    def close_dd(self):
        """
        Closes the pen
        :return: Boolean
        """
        return self.execute_command('closeDD')

    def put_dd_in_stable(self, id, source):
        """
        Moves a DD  to stable
        :param id: DD's id
        :return: Boolean
        """
        return self.execute_command('putInStable', [id, source])

    def put_dd_in_paddock(self, id, source):
        """
        Moves a DD to paddock
        :param id: DD's id
        :return: Boolean
        """
        return self.execute_command('putInPaddock', [id, source])

    def put_dd_in_inventory(self, id, source):
        """
        Moves a DD to paddock
        :param id: DD's id
        :return: Boolean
        """
        return self.execute_command('putInInventory', [id, source])

    def equip_dd(self, id, source):
        """
        Equips a DD
        :param id: DD's id
        :return: Boolean
        """
        return self.execute_command('equipDD', [id, source])

    def set_dd_xp(self, xp):
        """
        Sets the xp to give the equipped DD
        :param xp: 0 to 90%
        :return: Boolean
        """
        if not 0 <= xp <= 90:
            raise ValueError("Xp to give a DD must be between 0 and 90")
            # Dumb fuck
        return self.execute_command('setXpDD', [xp])

    def feed_dd(self, inv_id, quantity):
        """
        Feeds dd
        :param inv_id:
        :param quantity:
        :return:
        """
        return self.execute_command('feedDD', [inv_id, quantity])

    def mount_dd(self):
        """
        Mounts DD
        :return: Boolean
        """
        return self.execute_command('mountDD')

    def dismount_dd(self):
        """
        Dismounts DD
        :return: Boolean
        """
        return self.execute_command('dismountDD')

    def get_dd_stat(self):
        """
        Returns info on the equipped DD
        :return: False / [level, energy]
        """
        return self.execute_command('getDDStat')

    def fart(self):
        """
        Bot farts
        :return: Boolean
        """
        return self.execute_command('fart')

    def get_zaapi_cell(self):
        """
        Gives the maps zaapi position
        :return: False / cell
        """
        return self.execute_command('getZaapiCell')

    def use_zaapi(self, destination):
        """
        Uses the zaap to go to destination
        :param destination: coords (ex: (-2, 0))
        :return: Boolean
        """
        destination = ast.literal_eval(str(destination).replace('[', '(').replace(']', ')'))
        return self.execute_command('useZaapi', [destination])

    def use_item(self, inv_id):
        """
        Uses an item of the inventory
        :param inv_id: inventory id of the item to use
        :return: Boolean
        """
        return self.execute_command('useItem', [inv_id])

__author__ = 'Alexis'
