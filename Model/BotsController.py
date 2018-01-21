from Model.HighLevelFunctions import HighLevelFunctions
from Model.LowLevelFunctions import LowLevelFunctions
from Model.Interface import PipeToJava
from Model.Bot import Bot


class BotController:
    def __init__(self):
        self.pipe = PipeToJava(headless=True)
        self.llf = LowLevelFunctions()
        self.bots = []

    def new_bot(self, bot_id, credentials):
        self.bots.append(Bot(self.pipe, bot_id, credentials, self.llf))
        return self.bots[-1]
