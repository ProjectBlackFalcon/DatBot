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
with open('../Utils/HuntLogs.txt', 'r') as f:
    log = ''.join(f.readlines())
bot.llf.hunts_to_db('Test', 0, True, log, 'test')
bot.interface.disconnect()
pipe.p.terminate()
