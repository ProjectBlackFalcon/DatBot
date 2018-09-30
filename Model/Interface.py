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
        args = ['java', '-jar', '../BotTest.jar']
        if not headless:
            args.append("true")

        self.p = Popen(args, stdin=PIPE, stdout=PIPE, bufsize=4096, close_fds=on_posix)
        self.q = Queue()
        self.t = Thread(target=self.enqueue_output, args=(self.p.stdout, self.q))
        self.t.daemon = True
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
        self.new_hunt_timer = 0
        self.color = color
        self.end_color = '\033[0m'
        self.hdv_opended = False

    def add_command(self, command, parameters=None):
        # <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
        message = '{};{};i;cmd;{};{}\r\n'.format(self.bot.id, self.current_id, command, parameters)
        self.bot.llf.log(self.bot, '[Interface {}] Sending : {}'.format(self.bot.id, message.strip()))
        self.current_id += 1
        self.pipe.p.stdin.write(bytes(message, 'utf-8'))
        self.pipe.p.stdin.flush()
        return self.current_id-1

    def wait_for_return(self, message_id, timeout=5*60):
        # print('[Interface] Waiting for response...')
        ret_val = None
        message_queue = []
        start = time.time()
        while ret_val is None and time.time()-start < timeout:
            partial_message = '{};{};m;rtn'.format(self.bot.id, message_id)
            buffer = self.pipe.get_buffer()
            start = time.time() if self.bot.in_fight else start  # prevent timeout if in fight
            for message in buffer:
                if int(message.split(';')[0]) == self.bot.id and message not in message_queue:
                    self.bot.llf.log(self.bot, '[Interface {}] Recieved : {}'.format(self.bot.id, message))
                    message_queue.append(message)

            for message in message_queue:
                if partial_message in message and not self.bot.in_fight:
                    ret_val = ast.literal_eval(message.split(';')[-1])
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                elif 'info;combat;["start"]' in message:
                    self.bot.llf.log(self.bot, '[Fight {}] Started'.format(self.bot.id))
                    start_fight = time.time()
                    self.bot.in_fight = True
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                elif 'info;combat;["end"]' in message:
                    self.bot.llf.log(self.bot, '[Fight {}] Ended in {} mins'.format(self.bot.id, round((time.time()-start_fight)/60, 1)))
                    self.bot.in_fight = False
                    self.pipe.remove_from_buffer(self.bot.id, int(message.split(';')[1]))
                    del message_queue[message_queue.index(message)]
                    self.get_player_stats()
                elif 'info;disconnect;[True]' in message:
                    self.bot.llf.log(self.bot, '[Interface {}] Disconnected'.format(self.bot.id))
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
            self.bot.llf.log(self.bot, '[Interface {}] ERROR : \n{}'.format(self.bot.id, e.args))
            with open('../Utils/InterfaceErrors.txt', 'a') as f:
                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                f.write(traceback.format_exc())
            time.sleep(60)

    def connect(self, max_tries=5):
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
        tries = 0
        while not self.bot.connected and tries < max_tries:
            self.bot.occupation = 'Connecting'
            self.bot.hf.update_db()
            success = self.execute_command('connect', connection_param)
            tries += 1
            self.bot.connected = success[0]
            if self.bot.connected:
                self.get_player_stats()
                self.get_sub_left()
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                self.bot.position = (current_map, current_worldmap)
                dd_stats = self.get_dd_stat()
                if dd_stats[0]:
                    self.bot.mount = 'equipped'
                    self.mount_dd()
                    if dd_stats[0] < 100:
                        self.set_dd_xp(90)
                    else:
                        self.set_dd_xp(0)
                else:
                    self.bot.mount = self.bot.llf.get_mount_situation(self.bot.credentials['name'])
                    if self.bot.mount == 'resting':
                        self.bot.hf.fetch_bot_mobile()
                return [True]
            else:
                time.sleep(max(15, tries*30))
        return [False]

    def disconnect(self):
        """
        Disconnects the bot instance
        :return:
        """
        success = [False]
        if self.bot.connected:
            dd_stats = self.bot.interface.get_dd_stat()
            if dd_stats[0]:
                level, energy, idx = dd_stats
                if energy < 1000:
                    self.bot.hf.drop_bot_mobile(idx)
            success = [self.execute_command('disconnect')[0]]
            if success[0]:
                self.get_player_stats()
                self.bot.llf.log(self.bot, '[Position {}] {}'.format(self.bot.id, 'OFFLINE'))
                self.bot.connected = False
                self.bot.occupation = 'Sleeping'
                self.bot.hf.update_db()
                self.bot.llf.push_log_file('../packetErrors.txt', 'PacketErrors')
        return success

    def get_map(self):
        """
        Gets the map the player is on
        :return: coords, cell, worldmap, mapID
        """
        current_map, current_cell, current_worldmap, map_id = self.execute_command('getMap')
        self.bot.position = (current_map, current_worldmap)
        self.bot.llf.log(self.bot, '[Position {}] {}'.format(self.bot.id, current_map))
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
        stats = stats[0]
        self.bot.inventory.kamas = stats['Inventory']['Kamas']
        self.bot.inventory.items = stats['Inventory']['Items']
        self.bot.characteristics.level = stats['Lvl']
        self.bot.characteristics.health_percent = stats['Health']
        self.bot.characteristics.xp = stats['Xp']
        self.bot.characteristics.xp_next_level_floor = stats['XpNextLevelFloor']
        self.bot.characteristics.weight = stats['Weight']
        self.bot.characteristics.weight_max = stats['WeightMax']
        self.bot.characteristics.jobs = stats['Job']
        self.bot.characteristics.int = stats['Caracs']['Int']
        self.bot.characteristics.agi = stats['Caracs']['Agi']
        self.bot.characteristics.cha = stats['Caracs']['Cha']
        self.bot.characteristics.fo = stats['Caracs']['Fo']
        self.bot.characteristics.vi = stats['Caracs']['Vi']
        self.bot.characteristics.sa = stats['Caracs']['Sa']
        self.bot.characteristics.available_stat_points = stats['Caracs']['Available']

        self.bot.inventory.equip_preferred_stuff()
        if self.bot.characteristics.available_stat_points:
            caracs_to_augment = self.bot.llf.get_caracs_to_augment(self.bot)
            for carac in caracs_to_augment:
                self.assign_carac_points(carac[0], carac[1])
        return stats

    def harvest_resource(self, cell):
        """
        Harvests the resource on the cell given
        :param cell: cell number
        :return: [id, number_harvested, new_pods, max_pods], or combat or false
        """
        ret_val = self.execute_command('harvest', [cell])
        self.get_player_stats()
        return ret_val

    def go_to_astrub(self):
        """
        Goes to Astrub and makes the player exit the building (should arrive at 6, -19, cell 397, worldmap 1)
        :return: Boolean
        """
        return self.execute_command('goAstrub')

    def go_to_incarnam(self):
        """
        Enters the building and uses the gate to go to Incarnam
        :return: Boolean
        """
        return self.execute_command('goIncarnam')

    def get_bank_door(self):
        return self.execute_command('getBankDoor')

    def enter_bank(self):
        """
        Enters the bank on the map if there is one
        :return: Boolean
        """
        bank_door_cell = self.get_bank_door()[0]
        if bank_door_cell:
            self.move(bank_door_cell)
            return self.execute_command('goBank')
        else:
            return [False]

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

    def exit_bank(self):
        """
        Exits bank
        :return: Boolean
        """
        banks = {
            "(4, -18)": 409,
            "(-31, -54)": 409,
            "(-27, 35)": 409,
            "(2, -2)": 410,
            "(14, 25)": 480
        }
        return self.move(banks[str(self.bot.position[0])])

    def drop_in_bank_list(self, item_id_list):
        """
        Drops some items in bank
        :param item_id_list: [ItemID1, ItemID2...] / ['all']   Ids are inventory ids
        :return: New bank content, new inventory content
        """
        if item_id_list in ['All', 'all']:
            inventory_content, bank_content = self.execute_command('dropBankAll')
        else:
            inventory_content, bank_content = self.execute_command('dropBankList', item_id_list)
        self.bank_info = bank_content
        self.get_player_stats()
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
        self.get_player_stats()
        return inventory_content, bank_content

    def get_from_bank_list(self, item_id_list):
        """
        Retrieves some items in bank
        :param item_id_list: [ItemID1, ItemID2...] / ['All']
        :return: New bank content, new inventory content
        """
        inventory_content, bank_content = self.execute_command('getBankList', item_id_list)
        self.bank_info = bank_content
        self.get_player_stats()
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
        self.get_player_stats()
        return inventory_content, bank_content

    def put_kamas_in_bank(self, quantity):
        """
        Drops a specified quantity of kamas in bank
        :param quantity: quantity of kamas to drop
        :return: New bank content, new inventory content
        """
        kamas = self.bot.inventory.kamas
        if quantity in ['all', 'All'] or quantity > kamas:
            quantity = kamas
        inventory_content, bank_content = self.execute_command('dropBankKamas', [quantity])
        self.bank_info = bank_content
        self.get_player_stats()
        return inventory_content, bank_content

    def get_kamas_from_bank(self, quantity):
        """
        Retrieves a specified quantity of kamas from bank
        :param quantity: quantity of kamas to drop
        :return: New bank content, new inventory content
        """
        kamas = self.bank_info['Kamas']
        if quantity in ['all', 'All'] or quantity > kamas:
            quantity = kamas
        if quantity:
            inventory_content, bank_content = self.execute_command('getBankKamas', [quantity])
            self.bank_info = bank_content
            self.get_player_stats()
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
        if time.time()-self.new_hunt_timer < 10*60:
            time.sleep(10*60 - time.time() + self.new_hunt_timer + 30)
        if level == 'max':
            succes = self.execute_command('newHunt')
        else:
            succes = self.execute_command('newHunt', [level])
        if succes[0]:
            self.new_hunt_timer = time.time()
        return succes

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

    def get_tries_left(self):
        """
        Return how many hunt tries remain
        :return:
        """
        return self.execute_command('getTriesLeft')

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
        if not self.hdv_opended:
            ret_val = self.execute_command('openHdv')
            if ret_val[0]:
                self.hdv_opended = True
            return ret_val

    def close_hdv(self):
        """
        Closes the hdv
        :return: Boolean
        """
        if self.hdv_opended:
            ret_val = self.execute_command('closeHdv')
            if ret_val[0]:
                self.hdv_opended = False
            return ret_val

    def get_hdv_item_stats(self, item_id_list):
        """
        Gathers data about the item given
        :param item_id_list: {type_id1: [id1, id2, ...], ...}
        :return: False / [itemStats, ...] itemStats is a json formatted string with
            {
            id: [
            price1 (-1 if not for sale),
            price 10 (-1 if not for sale),
            price 100 (-1 if not for sale),
            average price (-1 if not for sale),
            stats as [[statName1, value1], [statName2, value2], ...] (statsNames are from RuneStats.json)
            }
        """

        # We're sending batches of 100 items max (there are 7150 items, so it would take 2 hours to do it in one request)
        if type(item_id_list) is not list:
            item_id_list = [item_id_list]
        item_id_list1 = item_id_list[:len(item_id_list)//100*100]
        item_id_list_rest = item_id_list[len(item_id_list) // 100 * 100:]
        lists_to_send = [item_id_list[i*100:(i+1)*100] for i in range(len(item_id_list1)//100)]
        if len(lists_to_send) and len(item_id_list_rest):
            lists_to_send.append(item_id_list_rest)
        elif len(item_id_list_rest):
            lists_to_send = [item_id_list_rest]
        ret_val = {}
        for lst in lists_to_send:
            self.bot.llf.log(self.bot, '[Interface {}] Fetching batch {}/{}...'.format(self.bot.id, lists_to_send.index(lst)+1, len(lists_to_send)))
            data_sent = {}
            for item_id in lst:
                type_id = self.bot.resources.id2type[str(item_id)]
                if type_id in data_sent.keys():
                    data_sent[type_id].append(item_id)
                else:
                    data_sent[type_id] = [item_id]
            ret = self.execute_command('getHdvItemStats', [data_sent])[0]
            self.bot.llf.log(self.bot, '[Interface {}] Done'.format(self.bot.id))
            if lst != lists_to_send[-1]:
                Thread(target=self.bot.llf.resource_item_to_db, args=(self.bot, ret, 'Items')).start()
            else:
                self.bot.llf.resource_item_to_db(self.bot, ret, 'Items')
            ret_val.update(ret)
        return ret_val

    def get_hdv_resource_stats(self, item_id_list):
        """
        Gathers data about the item given
        :param item_id_list: item id list
        :return: False / list of [id, price1, price 10, price 100, average price]
        """
        if type(item_id_list) is not list:
            item_id_list = [item_id_list]
        item_id_list1 = item_id_list[:len(item_id_list) // 100 * 100]
        item_id_list_rest = item_id_list[len(item_id_list) // 100 * 100:]
        lists_to_send = [item_id_list[i * 100:(i + 1) * 100] for i in range(len(item_id_list1) // 100)]
        if len(lists_to_send) and len(item_id_list_rest):
            lists_to_send.append(item_id_list_rest)
        elif len(item_id_list_rest):
            lists_to_send = [item_id_list_rest]
        ret_val = []
        for lst in lists_to_send:
            self.bot.llf.log(self.bot, '[Interface {}] Fetching batch {}/{}...'.format(self.bot.id, lists_to_send.index(lst) + 1, len(lists_to_send)))
            ret = self.execute_command('getHdvResourceStats', lst)[0]
            self.bot.llf.log(self.bot, '[Interface {}] Done'.format(self.bot.id))
            if lst != lists_to_send[-1]:
                Thread(target=self.bot.llf.resource_item_to_db, args=(self.bot, ret, 'Resources')).start()
            else:
                self.bot.llf.resource_item_to_db(self.bot, ret, 'Resources')
            ret_val += ret
        return ret_val

    def sell_item(self, item_id, batch_size, batch_number, price):
        """
        Sells an item as a batch
        :param item_id: Item id
        :param batch_size: 1, 10, or 100 items
        :param batch_number: Number of batches to sell
        :return: Boolaen
        """
        ret_val = self.execute_command('sellItem', [item_id, batch_size, batch_number, price])
        self.get_player_stats()
        return ret_val

    def modify_price(self, item_id, batch_size, new_price):
        """
        Modifies the selling price of an item
        :param item_id: item id
        :param batch_size: which batch size to modify (1, 10, 100)
        :param new_price: New price
        :return: Boolean
        """
        ret_val = self.execute_command('modifyPrice', [item_id, batch_size, new_price])
        self.get_player_stats()
        return ret_val

    def withdraw_item(self, item_id, batch_size, batch_number):
        """
        Cancels a selling order
        :param item_id: item ID
        :param batch_size: which batch size to withdraw (1, 10, 100)
        :param batch_number: Number of batches to withdraw
        :return: Boolean
        """
        ret_val = self.execute_command('withdrawItem', [item_id, batch_size, batch_number])
        self.get_player_stats()
        return ret_val

    def buy_resource(self, item_id, batch_size, batch_number, max_batch_price):
        # TODO
        """
        Buys a resource or group of resources
        :param item_id: item ID
        :param batch_size: which batch size to withdraw (1, 10, 100)
        :param batch_number: Number of batches to withdraw
        :param max_batch_price: Upper price limit
        :return: [number of items bought, total money spent]
        """
        type_id = self.bot.resources.id2type[str(item_id)]
        return self.execute_command('buyResource', [item_id, type_id, batch_size, batch_number, max_batch_price])

    def enter_fm(self):
        # TODO
        """
        Enters FM workshop (will be on the right spot)
        :return: Boolean
        """
        return self.execute_command('enterFM')

    def exit_fm(self):
        # TODO
        """
        Exits FM workshop
        :return: Boolean
        """
        return self.execute_command('exitFM')

    def open_item_breaker(self):
        # TODO
        """
        Opens the item breaker GUI
        :return: Boolean
        """
        return self.execute_command('openItemBreaker')

    def close_item_breaker(self):
        # TODO
        """
        Closes the item breaker GUI
        :return: Boolean
        """
        return self.execute_command('closeItemBreaker')

    def break_items(self, item_inv_id_list):
        # TODO
        """
        Breaks the items
        :param item_inv_id_list: List of items to break
        :return: False / Stats on the runes obtained
        """
        return self.execute_command('breakItems', item_inv_id_list)

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
        ret_val = self.execute_command('putInInventory', [id, source])
        self.get_player_stats()
        return ret_val

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
        return self.execute_command('setXpDD', [xp])

    def feed_dd(self, inv_id, quantity):
        """
        Feeds dd
        :param inv_id:
        :param quantity:
        :return:
        """
        ret_val = self.execute_command('feedDD', [inv_id, quantity])
        self.get_player_stats()
        return ret_val

    def mount_dd(self):
        """
        Mounts DD
        :return: Boolean
        """
        if self.bot.characteristics.level >= 60:
            return self.execute_command('mountDD')
        return False

    def dismount_dd(self):
        """
        Dismounts DD
        :return: Boolean
        """
        return self.execute_command('dismountDD')

    def get_dd_stat(self):
        """
        Returns info on the equipped DD
        :return: False / [level, energy, id]
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
        ret_val = self.execute_command('useItem', [inv_id])
        self.get_player_stats()
        return ret_val

    def assign_carac_points(self, carac_name, number):
        """
        Assigns carac points for the character
        :param carac_name: Int, Agi, Cha, Fo, Vi, Sa
        :param number: Number of points to use
        :return: Boolean
        """
        ret_val = self.execute_command('assignCaracPoints', [carac_name, number])
        return ret_val

    def equip_item(self, inv_id, slot):
        """
        Equips the selected item in the slot
        :param inv_id: Inventory id of the selected item
        :param slot: Slot the item should go to
        :return: Boolean
        """
        return self.execute_command('equipItem', [inv_id, slot])

    def de_equip_item(self, inv_id):
        """
        De-equips the item
        :param inv_id: inventory id of the item to de-equip
        :return: Boolean
        """
        return self.execute_command('deEquipItem', [inv_id])

    def exit_brak_north(self):
        """
        Exits brak by using the interactive on map (-26, 31). The bot will already be on cell 110
        :return: Boolean
        """
        return self.execute_command('exitBrak')

    def get_sub_left(self):
        """
        Returns the number of seconds of sub time left
        :return: Int
        """
        ret_val = self.execute_command('getSubTime')
        self.bot.subscribed = ret_val[0] if ret_val[0] else 0
        return ret_val

    def enter_dd_territory(self):
        """
        Enters dd territory by using the interactive on map (-23, -1). The bot will already be on cell 387
        :return: Boolean
        """
        return self.execute_command('enterDDTerritory')

__author__ = 'Alexis'
