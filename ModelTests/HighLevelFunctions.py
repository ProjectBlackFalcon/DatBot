from Model.HighLevelFunctions import HighLevelFunctions
import time

hf = HighLevelFunctions(0)
hf.interface.connect('wublel7', 'wubwublel7', 'Dihydroquerina')

path = (
    ((-2, -4), None),
    ((-2, -5), None),
    ((-3, -5), None),
    ((-3, -6), None),
    ((-2, -6), None),
    ((-1, -6), None),
    ((-1, -5), None),
    ((0, -5), None),
    ((0, -4), None),
    ((-1, -4), None),
    ((1, -2), None),
    ((2, -2), None),
    ((3, -2), None),
    ((3, -1), None),
    ((2, -1), None),
    ((2, -0), None),
    ((1, 0), None),
    ((1, -1), None),
    ((0, -1), 516),
    ((0, -1), 139),
    ((0, -2), None),
    ((-1, -2), None),
    ((-1, -1), 8),
    ((-2, -1), None),
    ((-2, -0), None),
    ((-1, 0), None),
    ((0, 0), None),
)

for tile in path:
    hf.goto(tile[0], target_cell=tile[1], worldmap=2)
    hf.harvest_map()

while 1:
    time.sleep(1)
__author__ = 'Alexis'
