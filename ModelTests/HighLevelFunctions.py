from Model.Interface import PipeToJava
from Model.LowLevelFunctions import LowLevelFunctions
from Model.Bot import Bot
import time
from threading import Thread
import winsound


pipe = PipeToJava(headless=True)
bot_id = 0

credentials = {'username': 'Jemappellehenry2', 'password': 'azerty123henry', 'name': 'Baddosch', 'server': 'Julith'}
credentials1 = {'username': 'notabotatall', 'password': 'notabotatall0', 'name': 'Docteur-Vilamoule', 'server': 'Julith'}
credentials2 = {'username': 'wublel6', 'password': '32407c62d2f', 'name': 'Pot-ator', 'server': 'Julith'}

llf = LowLevelFunctions()
bot = Bot(pipe, bot_id, credentials, llf)
bot.interface.connect()
bot.hf.tresure_hunt()
winsound.PlaySound('..//Utils//sound.wav', winsound.SND_FILENAME)

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
# bot1.hf.goto((9, -10))
# bot2.hf.goto((9, -10))

while 1:
    time.sleep(1)

__author__ = 'Alexis'
