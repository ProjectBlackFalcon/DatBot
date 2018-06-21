from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from Interface import PipeToJava
import time

test_account_credentials = {'username': 'democraticamnesiac', 'password': 'answerflash2', 'name': 'Maxitreur', 'server': 'Julith'}
llf = LowLevelFunctions()
pipe = PipeToJava(headless=True)
bot = Bot(pipe, 0, test_account_credentials, llf, True)
bot.interface.connect()
time.sleep(2)
bot.interface.move(255)
bot.interface.move(256)
bot.interface.move(300)
bot.hf.goto((3, -3), worldmap=2)
bot.hf.goto((4, -3), worldmap=2, target_cell=300)

bot.interface.disconnect()
pipe.p.terminate()