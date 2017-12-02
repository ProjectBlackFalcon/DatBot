from Model.Interface import Interface
from Model.LowLevelFunctions import LowLevelFunctions
import time

i = Interface(0)
llf = LowLevelFunctions()
# print(i.get_map())
print(i.connect('wublel7', 'wubwublel7', 'Dihydroquerina'))
current_map, current_cell, current_worldmap, map_id = i.get_map()
time.sleep(1)
statue_cell = i.get_class_statue_cell()[0]
teleport_cell = llf.get_closest_walkable_neighbour_cell(statue_cell, current_cell, current_map, current_worldmap)
print(statue_cell, teleport_cell)
i.get_map()
time.sleep(60)

__author__ = 'Alexis'
