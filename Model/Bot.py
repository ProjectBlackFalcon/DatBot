from Interface import Interface
from HighLevelFunctions import HighLevelFunctions
from LowLevelFunctions import LowLevelFunctions
from Inventory import Inventory
from Characteristics import Characteristics


class Bot:
    def __init__(self, pipe, bot_id, credentials, llf, resources, subscribed, color=''):
        self.pipe = pipe
        self.id = bot_id
        self.credentials = credentials
        self.interface = Interface(self, color=color)
        self.llf = llf  # type: LowLevelFunctions
        self.resources = resources
        self.hf = HighLevelFunctions(self)
        self.occupation = None
        self.position = None
        self.connected = False
        self.in_fight = False
        self.subscribed = subscribed
        self.inventory = Inventory(self)
        self.characteristics = Characteristics()
        self.level = None
        self.mount = None
        self.llf.add_bot_db(self.credentials['username'], self.credentials['password'], self.credentials['name'], self.credentials['server'])
        self.schedule = self.llf.get_schedule(self.credentials['name'])
        with open('../Utils/BotsLogs{}.txt'.format(self.credentials['name']), 'w') as f:
            f.write('')

    def run(self, schedule_name=None):
        if sorted(self.llf.get_discovered_zaaps(self.credentials['name'])) != sorted([[-26, 35], [-32, -56], [5, -18], [1, -32], [-17, -47], [-3, -42], [-13, -28], [-5, -23], [-5, -8], [-2, 0], [3, -5], [7, -4], [5, 7], [-1, 13], [-1, 24], [10, 22], [13, 26], [-16, 1], [-25, 12], [-20, -20], [-27, -36]]):
            self.hf.discover_zaaps()
        self.hf.use_schedule(schedule_name)
