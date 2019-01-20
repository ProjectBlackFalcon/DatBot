from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from Interface import PipeToJava
import time
from Resources import Resources

test_account_credentials = {'username': 'xakupavaxubaly', 'password': 'SFPO1MVCYP438C3A1$', 'name': 'Tuth', 'server': 'Julith'}

resources = Resources()
llf = LowLevelFunctions(resources)
pipe = PipeToJava(headless=True)
bot = Bot(pipe, 0, test_account_credentials, llf, resources)

if bot.interface.connect():
    bot.hf.goto((1, -3), worldmap=2)
    bot.hf.fight_on_map(1)
    bot.interface.disconnect()

pipe.p.terminate()

