from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from Interface import PipeToJava
import time
from Resources import Resources

test_account_credentials = {'username': 'randomname0', 'password': 'notabot0', 'name': 'Romaru', 'server': 'Julith'}
resources = Resources()
llf = LowLevelFunctions(resources)
pipe = PipeToJava(headless=True)
bot = Bot(pipe, 0, test_account_credentials, llf, resources)

bot.interface.connect()
bot.hf.get_hdv_prices('Equipements')
bot.interface.disconnect()
pipe.p.terminate()
