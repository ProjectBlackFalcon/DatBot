from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from Interface import PipeToJava
import time
import os

print(os.getcwd())
test_account_credentials = {'username': 'democraticamnesiac', 'password': 'answerflash2', 'name': 'Maxitreur', 'server': 'Julith'}
llf = LowLevelFunctions()
pipe = PipeToJava(headless=True)
bot = Bot(pipe, 0, test_account_credentials, llf, True)
bot.interface.connect()
time.sleep(2)
bot.interface.move(255)
bot.interface.move(256)
bot.interface.move(300)
bot.interface.disconnect()
pipe.p.terminate()