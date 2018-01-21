from Model.Interface import PipeToJava
from Model.LowLevelFunctions import LowLevelFunctions
from Model.Bot import Bot
import time

pipe = PipeToJava(headless=True)
bot_id = 0
credentials = {'username': 'notabotatall', 'password': 'notabotatall0', 'name': 'Docteur-Vilamoule', 'server': 'Julith'}
llf = LowLevelFunctions()
bot = Bot(pipe, bot_id, credentials, llf)
print(bot.credentials)
# print(i.get_map())
# i.connect('wublel7', 'wubwublel7', 'Dihydroquerina')
# i.connect('wublel6', '32407c62d2f', 'Pot-ator')
bot.interface.connect()

'''
current_map, current_cell, current_worldmap, map_id = i.get_map()
time.sleep(1)
bank_door_cell = i.get_bank_door_cell()[0]
activate_cell = llf.get_closest_walkable_neighbour_cell(bank_door_cell, current_cell, current_map, current_worldmap)
print(bank_door_cell, activate_cell)
time.sleep(60)
'''
__author__ = 'Alexis'
