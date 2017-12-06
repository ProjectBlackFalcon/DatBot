import ast
from threading import Thread
import sys
from queue import Queue, Empty
from subprocess import Popen, PIPE
import time


class Interface:
    def __init__(self, bot_instance, headless=True):
        self.file_name = 'Interfile{}'.format(bot_instance)
        self.bot_instance = bot_instance
        self.current_id = 0
        self.bank_info = {}

        self.connected = False

        ON_POSIX = 'posix' in sys.builtin_module_names

        args = ['java', '-jar', '..//BotTest.jar']
        if not headless:
            args.append('true')

        self.p = Popen(args, stdin=PIPE, stdout=PIPE, bufsize=10, close_fds=ON_POSIX)
        self.q = Queue()
        t = Thread(target=self.enqueue_output, args=(self.p.stdout, self.q))
        t.daemon = True  # thread dies with the program
        t.start()

    def enqueue_output(self, out, queue):
        for line in iter(out.readline, b''):
            queue.put(line)
        out.close()

    def add_command(self, command, parameters=None):
        # <botInstance>;<msgId>;<dest>;<msgType>;<command>;[param1, param2...]
        message = '{};{};i;cmd;{};{}\r\n'.format(self.bot_instance, self.current_id, command, parameters)
        print('[Interface] Sending : ', message.strip())
        self.current_id += 1
        self.p.stdin.write(bytes(message, 'utf-8'))
        self.p.stdin.flush()
        return self.current_id-1

    def wait_for_return(self, message_id):
        print('[Interface] Waiting for response...')
        ret_val = None
        while ret_val is None:
            messages = []
            try:
                while 1:
                    read = self.q.get_nowait()
                    messages.append(read.strip().decode('latin-1'))
            except Empty:
                pass

            partial_message = '{};{};m;rtn'.format(self.bot_instance, message_id)
            for message in messages:
                # print(message)
                if partial_message in message:
                    # print(message)
                    ret_val = ast.literal_eval(message.split(';')[-1])
            time.sleep(0.1)

        print('[Interface] Recieved : ', ret_val)
        return tuple(ret_val)

    def connect(self, account, password, ig_name, server='Julith'):
        """
        Connects a bot instance
        :param account: bot account name
        :param password: bot account password
        :return: Boolean
        """
        msg_id = self.add_command('connect', [account, password, ig_name, server])
        sucess = self.wait_for_return(msg_id)
        self.connected = sucess
        return sucess

    def disconnect(self):
        """
        Disconnects the bot instance
        :return:
        """
        if self.connected:
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
        if item_id_list == 'all':
            items = self.get_player_stats()[0]['Inventory']['Items']
            item_id_list = [item[1] for item in items]
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
__author__ = 'Alexis'
