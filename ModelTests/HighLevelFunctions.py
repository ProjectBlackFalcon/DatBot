from Model.Interface import PipeToJava
from Model.LowLevelFunctions import LowLevelFunctions
from Model.Bot import Bot
import time
from threading import Thread
import winsound


pipe = PipeToJava(headless=True)
bot_id = 0

zaap_path_1 = [(4, -19), (-5, -23), (-13, -28), (-3, -42), (-17, -47), (-32, -56), (-27, -36), (-20, -20), (-16, 1), (-25, 12), (-15, 25), (-26, 35)]
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

credentials1 = {'username': 'notabotatall', 'password': 'notabotatall0', 'name': 'Docteur-Vilamoule', 'server': 'Julith'}
credentials2 = {'username': 'wublel5', 'password': 'notabot0', 'name': 'Ilancelet', 'server': 'Julith'}
credentials3 = {'username': 'wublel4', 'password': 'notabot0', 'name': 'Los-Flachos', 'server': 'Julith'}
credentials4 = {'username': 'wublel6', 'password': '32407c62d2f', 'name': 'Holle-holla-hollu', 'server': 'Julith'}
credentials5 = {'username': 'debugthemall', 'password': 'azertyuiop1', 'name': 'Le-Gros-Veineux', 'server': 'Julith'}


llf = LowLevelFunctions()
bot = Bot(pipe, bot_id, credentials5, llf)
bot.interface.connect()
start = time.time()

bot.hf.hunt_treasures(120)

winsound.PlaySound('..//Utils//sound.wav', winsound.SND_FILENAME)
print('Done in {} minutes'.format(round((time.time()-start)/60, 1)))


while 1:
    time.sleep(1)
__author__ = 'Alexis'
