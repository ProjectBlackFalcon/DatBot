import time
import ast


class Interface:
    def __init__(self, bot_instance):
        self.file_name = 'Interfile{}'.format(bot_instance)
        self.current_id = 0
        try:
            with open(self.file_name, 'r') as f:
                lines = f.readlines()
                self.current_id = int(lines[-1][0])
                f.close()
        except Exception:
            print('Creating bot file')

    def add_command(self, command, parameters=None):
        added = False
        while not added:
            try:
                self.current_id += 1
                with open(self.file_name, 'a') as f:
                    f.write('{};i;cmd;{};{}\n'.format(self.current_id, command, parameters))
                    f.close()
                added = True
            except Exception:
                pass
        return self.current_id

    def wait_for_return(self, command_id):
        ret_val = None

        while ret_val is None:
            with open(self.file_name, 'r') as f:
                lines = f.readlines()
                f.close()

            for line in lines:
                if str(command_id)+';m;rtn' in line:
                    ret_val = ast.literal_eval(line.strip().split(';')[4:][0])

            time.sleep(0.1)
        return ret_val

    def connect(self, account, password):
        request_id = self.add_command('connect', [account, password])
        return self.wait_for_return(request_id)

    def get_map(self):
        request_id = self.add_command('getMap')
        return self.wait_for_return(request_id)

    def move(self, cell):
        request_id = self.add_command('move', [cell])
        return self.wait_for_return(request_id)

__author__ = 'Alexis'
