import ast
from threading import Thread
import sys
from queue import Queue, Empty
from subprocess import Popen, PIPE


class Interface:
    def __init__(self, bot_instance):
        self.file_name = 'Interfile{}'.format(bot_instance)
        self.bot_instance = bot_instance
        self.current_id = 0

        ON_POSIX = 'posix' in sys.builtin_module_names
        self.p = Popen(['java', '-jar', 'test.jar'], stdin=PIPE, stdout=PIPE, bufsize=10, close_fds=ON_POSIX)
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
        self.current_id += 1
        self.p.stdin.write(bytes(message, 'utf-8'))
        self.p.stdin.flush()
        return self.current_id

    def wait_for_return(self, message_id):
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
                if partial_message in message:
                    ret_val = ast.literal_eval(message.split(';')[-1])

        if len(ret_val) > 1:
            return tuple(ret_val)
        else:
            return ret_val[0]

    def connect(self, account, password, ig_name, server):
        """
        Connects a bot instance
        :param account: bot account name
        :param password: bot account password
        :return: Boolean
        """
        msg_id = self.add_command('connect', [account, password, ig_name, server])
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

    def change_map(self, cell):
        """
        Moves the bot to an adjacent map
        :param cell: target cell number for map change
        :return: Boolean
        """
        msg_id = self.add_command('changeMap', [cell])
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
        :return: TODO
        """
        msg_id = self.add_command('getResources')
        return self.wait_for_return(msg_id)

__author__ = 'Alexis'
