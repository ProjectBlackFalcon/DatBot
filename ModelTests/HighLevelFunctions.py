from Model.Interface import PipeToJava
from Model.LowLevelFunctions import LowLevelFunctions
from Model.Bot import Bot
from threading import Thread
import random

pipe = PipeToJava(headless=True)
bot_id = 0
llf = LowLevelFunctions()

path = (
    ((-2, -4), None, 2),
    ((-2, -5), None, 2),
    ((-3, -5), None, 2),
    ((-3, -6), None, 2),
    ((-2, -6), None, 2),
    ((-1, -6), None, 2),
    ((-1, -5), None, 2),
    ((0, -5), None, 2),
    ((0, -4), None, 2),
    ((-1, -4), None, 2),
    ((1, -2), None, 2),
    ((2, -2), None, 2),
    ((3, -2), None, 2),
    ((3, -1), None, 2),
    ((2, -1), None, 2),
    ((2, -0), None, 2),
    ((1, 0), None, 2),
    ((1, -1), None, 2),
    ((0, -1), 516, 2),
    ((0, -1), 139, 2),
    ((0, -2), None, 2),
    ((-1, -2), None, 2),
    ((-1, -1), 8, 2),
    ((-2, -1), None, 2),
    ((-2, -0), None, 2),
    ((-1, 0), None, 2),
    ((0, 0), None, 2),
)

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


cred = {'username': 'wublel5', 'password': 'notabot0', 'name': 'Ilancelet', 'server': 'Julith'}

bot = Bot(pipe, 0, cred, llf, False, color='\033[92m')

bot.interface.connect()

pipe.t.join()
__author__ = 'Alexis'
