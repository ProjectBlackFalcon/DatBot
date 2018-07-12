from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from Interface import PipeToJava
import time

test_account_credentials = {'username': 'democraticamnesiac', 'password': 'answerflash2', 'name': 'Maxitreur', 'server': 'Julith'}
llf = LowLevelFunctions()
pipe = PipeToJava(headless=True)
bot = Bot(pipe, 0, test_account_credentials, llf, True)

bot.interface.connect()
while 1:
    bot.interface.get_player_stats()
    time.sleep(1)
bot.interface.disconnect()
pipe.p.terminate()
