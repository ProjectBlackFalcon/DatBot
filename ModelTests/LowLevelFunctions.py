from Model.LowLevelFunctions import LowLevelFunctions
import time

print(LowLevelFunctions().cell2coord(555))
print(LowLevelFunctions().coord2cell((29, 23)))

print(LowLevelFunctions().distance_cell(0, 14))

print(LowLevelFunctions().get_neighbour_cells(14))
__author__ = 'Alexis'