from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from Interface import PipeToJava
import time
from Resources import Resources
from FbBot import FbBot

test_account_credentials = {'username': 'randomname0', 'password': 'notabot0', 'name': 'Romaru', 'server': 'Julith'}
resources = Resources()
llf = LowLevelFunctions(resources)
pipe = PipeToJava(headless=True)
bot = Bot(pipe, 0, test_account_credentials, llf, resources)

while 1:
    bot.interface.connect()
    bot.hf.scrape_hdvs()
    bot.interface.disconnect()
    time.sleep(1800)

pipe.p.terminate()
