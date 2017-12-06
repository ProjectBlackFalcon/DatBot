from Model.Interface import Interface
from Model.LowLevelFunctions import LowLevelFunctions
import time

i = Interface(0)
llf = LowLevelFunctions()
# print(i.get_map())
i.connect('wublel7', 'wubwublel7', 'Dihydroquerina')
current_map, current_cell, current_worldmap, map_id = i.get_map()
time.sleep(1)
i.get_class_statue_cell()
bank_door_cell = i.get_bank_door_cell()[0]
activate_cell = llf.get_closest_walkable_neighbour_cell(bank_door_cell, current_cell, current_map, current_worldmap)
print(bank_door_cell, activate_cell)
time.sleep(60)

__author__ = 'Alexis'
