import ast
from threading import Thread
import sys
from queue import Queue, Empty
from subprocess import Popen, PIPE
import time


class PipeToJava:
    def __init__(self, headless=True):
        self.buffer = []
        on_posix = 'posix' in sys.builtin_module_names
        args = ['java', '-jar', '..//BotTest.jar']
        if not headless:
            args.append("true")

        self.p = Popen(args, stdin=PIPE, stdout=PIPE, bufsize=10, close_fds=on_posix)
        self.q = Queue()
        t = Thread(target=self.enqueue_output, args=(self.p.stdout, self.q))
        t.daemon = True  # thread dies with the program
        t.start()

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
    def __init__(self, bot):
        self.bot = bot  # type: Bot
        self.pipe = self.bot.pipe  # type: PipeToJava
        self.current_id = 0
        self.bank_info = {}

    def add_command(self, command, parameters=None):
        # <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
        message = '{};{};i;cmd;{};{}\r\n'.format(self.bot.id, self.current_id, command, parameters)
        print('[Interface] Sending : ', message.strip())
        self.current_id += 1
        self.pipe.p.stdin.write(bytes(message, 'utf-8'))
        self.pipe.p.stdin.flush()
        return self.current_id-1

    def wait_for_return(self, message_id):
        print('[Interface] Waiting for response...')
        ret_val = None
        message_queue = []
        while ret_val is None:
            partial_message = '{};{};m;rtn'.format(self.bot.id, message_id)
            buffer = self.pipe.get_buffer()
            for message in buffer:
                if int(message.split(';')[0]) == self.bot.id and message not in message_queue:
                    # print(message)
                    message_queue.append(message)

            for message in message_queue:
                if partial_message in message and not self.bot.in_fight:
                    ret_val = ast.literal_eval(message.split(';')[-1])
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                elif 'info;combat;["start"]' in message:
                    print('Fight Started')
                    self.bot.in_fight = True
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                elif 'info;combat;["end"]' in message:
                    print('Fight Ended')
                    self.bot.in_fight = False
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]

            time.sleep(0.1)
        if not self.bot.in_fight:
            print('[Interface] Recieved : ', ret_val)
            return tuple(ret_val)

    def connect(self):
        """
        Connects a bot instance
        :return: Boolean
        """
        connection_param = [
            self.bot.credentials['username'],
            self.bot.credentials['password'],
            self.bot.credentials['name'],
            self.bot.credentials['server']
        ]
        msg_id = self.add_command('connect', connection_param)
        sucess = self.wait_for_return(msg_id)
        self.bot.connected = sucess
        return sucess

    def disconnect(self):
        """
        Disconnects the bot instance
        :return:
        """
        if self.bot.connected:
            msg_id = self.add_command('disconnect')
            return self.wait_for_return(msg_id)

    def get_map(self):
        """
        Gets the map the player is on
        :return: coords, cell, worldmap, mapID
        """
        msg_id = self.add_command('getMap')
        return self.wait_for_return(msg_id)

    def move(self, cell):
        """
        Moves the bot on a map
        :param cell: target cell number
        :return: Boolean
        """
        msg_id = self.add_command('move', [cell])
        return self.wait_for_return(msg_id)

    def change_map(self, cell, direction):
        """
        Moves the bot to an adjacent map
        :param cell: target cell number for map change
        :param direction: cardnial direction as 'n', 's', 'w', 'e'
        :return: Boolean
        """
        msg_id = self.add_command('changeMap', [cell, direction])
        return self.wait_for_return(msg_id)

    def get_map_resources(self):
        """
        Gets the resources and their info for the map the player is on
        :return: TODO
        """
        msg_id = self.add_command('getResources')
        return self.wait_for_return(msg_id)

    def get_player_stats(self):
        """
        Get the bot player stats
        :return: {"current_pods": <>, "max_pods": <>, "level": <>, "job_levels": {"job_id": level, ...}}
        """
        msg_id = self.add_command('getStats')
        return self.wait_for_return(msg_id)

    def harvest_resource(self, cell):
        """
        Harvests the resource on the cell given
        :param cell: cell number
        :return: [id, number_harvested, new_pods, max_pods], or combat or false
        """
        msg_id = self.add_command('harvest', [cell])
        return self.wait_for_return(msg_id)

    def go_to_astrub(self):
        """
        Talks to the PNJ to go to Astrub
        :return: Boolean
        """
        msg_id = self.add_command('goAstrub')
        return self.wait_for_return(msg_id)

    def go_to_incarnam(self):
        """
        Uses a statue to go to Incarnam
        :return: Boolean
        """
        msg_id = self.add_command('goIncarnam')
        return self.wait_for_return(msg_id)

    def get_class_statue_cell(self):
        """
        Returns the cell id of the current map class statue, or False if there is none
        :return: [cell] or [False]
        """
        msg_id = self.add_command('getStatue')
        return self.wait_for_return(msg_id)

    def get_bank_door_cell(self):
        """
        Returns the cell id of the current map bank door, or False if there is none
        :return: [cell_in, cell_out] or [False]
        """
        msg_id = self.add_command('getBankDoor')
        return self.wait_for_return(msg_id)

    def enter_bank(self):
        """
        Uses a door to enter bank
        :return: Boolean
        """
        msg_id = self.add_command('goBank')
        return self.wait_for_return(msg_id)

    def open_bank(self):
        """
        Opens bank
        :return: items json / False
        """
        msg_id = self.add_command('openBank')
        bank_content = self.wait_for_return(msg_id)
        self.bank_info = bank_content[0]
        return bank_content

    def close_bank(self):
        """
        Closes Bank
        :return: Boolean
        """
        msg_id = self.add_command('closeBank')
        self.bank_info = {}
        return self.wait_for_return(msg_id)

    def drop_in_bank_list(self, item_id_list):
        """
        Drops some items in bank
        :param item_id_list: [ItemID1, ItemID2...] / ['all']
        :return: New bank content, new inventory content
        """
        if item_id_list in ['All', 'all']:
            msg_id = self.add_command('dropBankAll', item_id_list)
        else:
            msg_id = self.add_command('dropBankList', item_id_list)
        bank_content, inventory_content = self.wait_for_return(msg_id)
        self.bank_info = bank_content
        return bank_content, inventory_content

    def drop_in_bank_unique(self, item_id, quantity):
        """
        Drops a certain quantity of a certain item in inventory
        :param item_id: Item unique inventory id
        :param quantity: quantity of item to drop
        :return: New bank content, new inventory content
        """
        msg_id = self.add_command('dropBank', [item_id, quantity])
        bank_content, inventory_content = self.wait_for_return(msg_id)
        self.bank_info = bank_content
        return bank_content, inventory_content

    def get_from_bank_list(self, item_id_list):
        """
        Retrieves some items in bank
        :param item_id_list: [ItemID1, ItemID2...] / ['All']
        :return: New bank content, new inventory content
        """
        msg_id = self.add_command('getBankList', item_id_list)
        bank_content, inventory_content = self.wait_for_return(msg_id)
        self.bank_info = bank_content
        return bank_content, inventory_content

    def get_from_bank_unique(self, item_id, quantity):
        """
        Retrieves a certain quantity of a certain item in bank
        :param item_id: Item unique inventory id
        :param quantity: quantity of item to retrieve
        :return: New bank content, new inventory content
        """
        msg_id = self.add_command('getBank', [item_id, quantity])
        bank_content, inventory_content = self.wait_for_return(msg_id)
        self.bank_info = bank_content
        return bank_content, inventory_content

    def put_kamas_in_bank(self, quantity):
        """
        Drops a specified quantity of kamas in bank
        :param quantity: quantity of kamas to drop
        :return: New bank content, new inventory content
        """
        kamas = self.get_player_stats()[0]['Kamas']
        if quantity in ['all', 'All'] or quantity > kamas:
            quantity = kamas
        msg_id = self.add_command('dropBankKamas', [quantity])
        bank_content, inventory_content = self.wait_for_return(msg_id)
        self.bank_info = bank_content
        return bank_content, inventory_content

    def get_kamas_from_bank(self, quantity):
        """
        Retrieves a specified quantity of kamas from bank
        :param quantity: quantity of kamas to drop
        :return: New bank content, new inventory content
        """
        kamas = self.bank_info['Kamas']
        if quantity in ['all', 'All'] or quantity > kamas:
            quantity = kamas
        msg_id = self.add_command('getBankKamas', [quantity])
        bank_content, inventory_content = self.wait_for_return(msg_id)
        self.bank_info = bank_content
        return bank_content, inventory_content

    def get_hunting_hall_door_cell(self):
        """
        Returns the cell id of the hunting hall door, or False if there is none
        :return: [cell_in, cell_out] or [False]
        """
        msg_id = self.add_command('getHuntingHallDoorCell')
        return self.wait_for_return(msg_id)

    def enter_hunting_hall(self):
        """
        Uses the door to enter the hunting hall, then moves to the main room
        :return: Boolean
        """
        msg_id = self.add_command('goHuntingHall')
        return self.wait_for_return(msg_id)

    def exit_hunting_hall(self):
        """
        Goes back the the first room, and exits the hall
        :return: Boolean
        """
        msg_id = self.add_command('exitHuntingHall')
        return self.wait_for_return(msg_id)

    def get_new_hunt(self, level='max'):
        """
        The bot gets a new hunt at the tresure hunt thing. It should already be standing at the right spot
        :param level:
        :return: Boolean
        """
        if level == 'max':
            msg_id = self.add_command('newHunt')
        else:
            msg_id = self.add_command('newHunt', [level])
        return self.wait_for_return(msg_id)

    def hunt_is_active(self):
        """
        Checks wether a hunt is active or not
        :return: Boolean
        """
        msg_id = self.add_command('huntActive')
        return self.wait_for_return(msg_id)

    def abandon_hunt(self):
        """
        The bot drops the hunt
        :return: Boolean
        """
        msg_id = self.add_command('abandonHunt')
        return self.wait_for_return(msg_id)

    def get_hunt_start(self):
        """
        Returns the starting pos of the hunt
        :return:
        """
        msg_id = self.add_command('getHuntStart')
        return self.wait_for_return(msg_id)

    def get_clues_left(self):
        """
        Gets the number of clues left in this step
        :return:
        """
        msg_id = self.add_command('getCluesLeft')
        return self.wait_for_return(msg_id)

    def get_steps_left(self):
        """
        Gets the number of steps left in the hunt
        :return:
        """
        msg_id = self.add_command('getStepsLeft')
        return self.wait_for_return(msg_id)

    def get_hunt_clue(self):
        """
        Returns the clue the bot should be looking for
        :return: [clue : String, direction : 'n','s','w','e']
        """
        msg_id = self.add_command('getClue')
        return self.wait_for_return(msg_id)

    def validate_hunt_clue(self):
        """
        Validates the clue (the little flag in the gui)
        :return: boolean
        """
        msg_id = self.add_command('validateClue')
        return self.wait_for_return(msg_id)

    def check_for_phorror(self):
        """
        If a phorror is on the map, return it's name
        :return: String or False
        """
        msg_id = self.add_command('checkPhorror')
        return self.wait_for_return(msg_id)

    def validate_hunt_step(self):
        """
        Validates the full step.
        :return: Boolean
        """
        msg_id = self.add_command('validateStep')
        return self.wait_for_return(msg_id)

    def start_hunt_fight(self):
        """
        Starts the fight
        :return: Boolean
        """
        msg_id = self.add_command('huntFight')
        return self.wait_for_return(msg_id)

    def enter_heavenbag(self):
        """
        Enters heavenbag
        :return: Boolean
        """
        msg_id = self.add_command('enterBag')
        return self.wait_for_return(msg_id)

    def exit_heavenbag(self):
        """
        Exits heavenbag
        :return: Boolean
        """
        msg_id = self.add_command('exitBag')
        return self.wait_for_return(msg_id)

    def get_zaap_cell(self):
        """
        Returns the cell id of the current map zaap, or False if there is none
        :return: [cell] or [False]
        """
        msg_id = self.add_command('getZaap')
        return self.wait_for_return(msg_id)

    def use_zaap(self, destination):
        """
        Uses the zaap to go to destination
        :param destination: coords (ex: (-2, 0))
        :return: Boolean
        """
        destination = ast.literal_eval(str(destination).replace('[', '(').replace(']', ')'))
        msg_id = self.add_command('useZaap', [destination])
        return self.wait_for_return(msg_id)

    def get_monsters(self):
        """
        Gives the positions and types of monsters on the map
        :return: [[mob_id, mob_name_ref, cell], [...]]
        """
        msg_id = self.add_command('getMonsters')
        return self.wait_for_return(msg_id)

    def attack_monster(self, mob_id):
        """
        Attacks the mob specified my mob_id. The bot must be on the same cell.
        :param mob_id: Id of the mob to attack. Given by get_monsters.
        :return:
        """
        msg_id = self.add_command('attackMonster', [mob_id])
        return self.wait_for_return(msg_id)

    def open_hdv(self):
        """
        Tries to open the map's hdv. If sucessful, returns what items are being sold.
        :return: False / "empty" / [[name, id, batch_size, price], [...]]
        """
        msg_id = self.add_command('openHdv')
        return self.wait_for_return(msg_id)

    def get_hdv_item_stats(self, item_id):
        """
        Gathers data about the item given
        :param item_id: item id
        :return: False / [price1, price 10, price 100]
        """
        msg_id = self.add_command('getHdvItemStats', [item_id])
        return self.wait_for_return(msg_id)

    def sell_item(self, item_id, batch_size, batch_number, price):
        """
        Sells an item as a batch
        :param item_id: Item id
        :param batch_size: 1, 10, or 100 items
        :param batch_number: Number of batches to sell
        :return: Boolaen
        """
        msg_id = self.add_command('sellItem', [item_id, batch_size, batch_number, price])
        return self.wait_for_return(msg_id)

    def modify_price(self, item_id, batch_size, new_price):
        """
        Modifies the selling price of an item
        :param item_id: item id
        :param batch_size: which batch size to modify (1, 10, 100)
        :param new_price: New price
        :return: Boolean
        """
        msg_id = self.add_command('modifyPrice', [item_id, batch_size, new_price])
        return self.wait_for_return(msg_id)

    def withdraw_item(self, item_id, batch_size, batch_number):
        """
        Cancels a selling order
        :param item_id: item ID
        :param batch_size: which batch size to withdraw (1, 10, 100)
        :param batch_number: Number of batches to withdraw
        :return: Boolean
        """
        msg_id = self.add_command('withdrawItem', [item_id, batch_size, batch_number])
        return self.wait_for_return(msg_id)

__author__ = 'Alexis'
