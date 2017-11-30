from Model.Interface import Interface
import time

i = Interface(0)
# print(i.get_map())
print(i.connect('wublel7', 'wubwublel7', 'Dihydroquerina'))
i.get_map()
time.sleep(1)
i.get_map_resources()
i.get_map()
time.sleep(60)

__author__ = 'Alexis'
