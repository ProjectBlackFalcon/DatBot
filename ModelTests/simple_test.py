from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from Interface import PipeToJava
import time
from Resources import Resources
from FbBot import FbBot

test_account_credentials = {'username': 'randomname2', 'password': 'notabot0', 'name': 'Sepre', 'server': 'Julith'}
resources = Resources()
llf = LowLevelFunctions(resources)
pipe = PipeToJava(headless=True)
bot = Bot(pipe, 0, test_account_credentials, llf, resources)
fb = FbBot(llf)

while 1:
    bot.interface.connect()
    bot.hf.scrape_hdvs()
    bot.interface.disconnect()
    fb.send_alerts()
    time.sleep(1800)

pipe.p.terminate()
