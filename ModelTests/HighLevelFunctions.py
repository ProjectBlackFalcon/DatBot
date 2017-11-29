from Model.HighLevelFunctions import HighLevelFunctions
import time

hf = HighLevelFunctions(0)
hf.interface.connect('wublel7', 'wubwublel7', 'Dihydroquerina')

'''
for i in range(7):
    hf.goto((6-1-i, -24))
    with open('res_log.text', 'a') as f:
        f.write(str(hf.interface.get_map_resources())+'\n')
'''
hf.goto((-1, -2), worldmap=2)
hf.harvest_map()


while 1:
    time.sleep(1)
__author__ = 'Alexis'
