from Interface import Interface
from HighLevelFunctions import HighLevelFunctions
from LowLevelFunctions import LowLevelFunctions


class Bot:
    def __init__(self, pipe, bot_id, credentials, llf, subscribed, color=''):
        self.pipe = pipe
        self.id = bot_id
        self.credentials = credentials
        self.interface = Interface(self, color=color)
        self.llf = llf  # type: LowLevelFunctions
        self.hf = HighLevelFunctions(self)
        self.occupation = None
        self.position = None
        self.connected = False
        self.in_fight = False
        self.subscribed = subscribed
        self.kamas = None
        self.level = None
        self.schedule = self.llf.get_schedule(self.credentials['name'])
        self.mount = None

    def run(self, schedule_name=None):
        self.hf.use_schedule(schedule_name)
