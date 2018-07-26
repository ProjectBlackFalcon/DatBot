from Interface import PipeToJava
from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
from threading import Thread
import random
from Resources import Resources

pipe = PipeToJava(headless=True)
bot_id = 0
resources = Resources()
llf = LowLevelFunctions(resources)

colors = [
    '\33[31m',
    '\33[33m',
    '\33[34m',
    '\33[35m',
    '\33[36m',
    '\33[91m',
    '\33[92m',
    '\33[93m',
    '\33[94m',
    '\33[95m',
    '\33[96m',
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
    {'username': 'wublel2', 'password': 'notabot0', 'name': 'Gradopr', 'server': 'Julith'},
]

bots = []
threads = []
for cred in credentials:
    bots.append(Bot(pipe, credentials.index(cred), cred, llf, resources, color=colors[credentials.index(cred)]))

for bot in bots:
    threads.append(Thread(target=bot.run))
    threads[-1].name = bot.credentials['name']
    threads[-1].start()
for thread in threads:
    thread.join()

pipe.t.join()
__author__ = 'Alexis'
