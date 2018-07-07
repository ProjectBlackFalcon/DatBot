from Interface import PipeToJava
from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from threading import Thread
import random

pipe = PipeToJava(headless=True)
bot_id = 0
llf = LowLevelFunctions()

colors = [
    '\033[92m',
    '\033[94m',
    '\033[93m',
    '\033[95m',
    '\033[91m',
    '\033[96m',
    '\033[99m'
]
random.shuffle(colors)

'''
{'username': 'wublel6', 'password': 'notabot0', 'name': 'Holle-holla-hollu', 'server': 'Julith'}
{'username': 'wublel9', 'password': 'notabot0', 'name': 'Sayerses', 'server': 'Julith'}
{'username': 'wublel11', 'password': 'notabot0', 'name': 'Alvestana', 'server': 'Furye'}
{'username': 'wublel2', 'password': 'notabot0', 'name': 'Scalpelementaire', 'server': 'Julith'}
{'username': 'wublel2', 'password': 'notabot0', 'name': 'Gradopr', 'server': 'Julith'}
{'username': 'wublel5', 'password': 'notabot0', 'name': 'Ilancelet', 'server': 'Julith'},
{'username': 'wublel12', 'password': 'notabot0', 'name': 'Draideac', 'server': 'Julith'},
'''


credentials = [
    {'username': 'democraticamnesiac', 'password': 'answerflash2', 'name': 'Maxitreur', 'server': 'Julith'},
    {'username': 'wublel2', 'password': 'notabot0', 'name': 'Gradopr', 'server': 'Julith'}
]

bots = []
threads = []
for cred in credentials:
    bots.append(Bot(pipe, credentials.index(cred), cred, llf, False, color=colors[credentials.index(cred)]))

for bot in bots:
    threads.append(Thread(target=bot.run))
    threads[-1].name = bot.credentials['name']
    threads[-1].start()
for thread in threads:
    thread.join()

pipe.t.join()
__author__ = 'Alexis'
