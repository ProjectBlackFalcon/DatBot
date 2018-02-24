from Model.Interface import PipeToJava
from Model.LowLevelFunctions import LowLevelFunctions
from Model.Bot import Bot
import time

pipe = PipeToJava(headless=True)
bot_id = 0
# credentials = {'username': 'notabotatall', 'password': 'notabotatall0', 'name': 'Docteur-Vilamoule', 'server': 'Julith'}
credentials = {'username': 'wublel5', 'password': 'notabot0', 'name': 'Ilancelet', 'server': 'Julith'}
llf = LowLevelFunctions()
bot = Bot(pipe, bot_id, credentials, llf)
# print(i.get_map())
# i.connect('wublel7', 'wubwublel7', 'Dihydroquerina')
# i.connect('wublel6', '32407c62d2f', 'Pot-ator')

bot.interface.connect()
inventory = bot.interface.get_player_stats()[0]['Inventory']['Items']
bot.interface.open_hdv()
bot.interface.get_hdv_item_stats(15263)
print(llf.get_inventory_id(inventory, 15263))
# bot.interface.modify_price(15263, 1, 200)

'''
current_map, current_cell, current_worldmap, map_id = i.get_map()
time.sleep(1)
bank_door_cell = i.get_bank_door_cell()[0]
activate_cell = llf.get_closest_walkable_neighbour_cell(bank_door_cell, current_cell, current_map, current_worldmap)
print(bank_door_cell, activate_cell)
time.sleep(60)
'''
__author__ = 'Alexis'
