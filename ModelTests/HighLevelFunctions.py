from Model.Interface import PipeToJava
from Model.LowLevelFunctions import LowLevelFunctions
from Model.Bot import Bot
import time

# credentials = {'username': 'wublel6', 'password': '32407c62d2f', 'name': 'Pot-ator'}

pipe = PipeToJava(headless=True)
bot_id = 0
credentials = {'username': 'notabotatall', 'password': 'notabotatall0', 'name': 'Docteur-Vilamoule', 'server': 'Julith'}
llf = LowLevelFunctions()
bot = Bot(pipe, bot_id, credentials, llf)

bot.interface.connect()

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

# hf.harvest_path(path, do_not_harvest=['Ble'])
bot.hf.goto((7, -19))

while 1:
    time.sleep(1)

__author__ = 'Alexis'
