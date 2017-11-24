import time
import ast
import mysql.connector

class Interface:
    def __init__(self, bot_instance):
        self.file_name = 'Interfile{}'.format(bot_instance)
        self.bot_instance = bot_instance

    def add_command(self, command, parameters=None):
        start = time.time()
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec", database="wz3xj6_spec")
        cursor = conn.cursor()
        request_content = ('i', 'cmd', command, str(parameters), self.bot_instance)
        cursor.execute("""UPDATE MIexchange SET dest=%s, type=%s, command=%s, args=%s WHERE botID=%s""", request_content)
        conn.commit()
        conn.close()
        print('Sent in', round(time.time()-start, 2), 's')

    def wait_for_return(self):
        ret_val = None
        while ret_val is None:
            conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec", database="wz3xj6_spec")
            cursor = conn.cursor()
            cursor.execute("""SELECT dest, type, command, args FROM MIexchange WHERE botID=%s""", (self.bot_instance, ))
            row = cursor.fetchall()[0]
            if row[1] == 'rtn' and row[0] == 'm':
                ret_val = ast.literal_eval(row[3])
        return ret_val

    def connect(self, account, password):
        self.add_command('connect', [account, password])
        return self.wait_for_return()

    def get_map(self):
        self.add_command('getMap')
        return self.wait_for_return()

    def move(self, cell):
        self.add_command('move', [cell])
        return self.wait_for_return()

__author__ = 'Alexis'
