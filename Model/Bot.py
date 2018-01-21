from Model.Interface import Interface
from Model.HighLevelFunctions import HighLevelFunctions


class Bot:
    def __init__(self, pipe, bot_id, credentials, llf):
        self.pipe = pipe
        self.id = bot_id
        self.credentials = credentials
        self.interface = Interface(self)
        self.llf = llf
        self.hf = HighLevelFunctions(self)
        self.occupation = None
        self.position = None
        self.connected = False
        self.in_fight = False
