from Model.Interface import Interface
import time

i = Interface(0)
# print(i.get_map())
print(i.connect('wublel7', 'wubwublel7', 'Dihydroquerina'))
print(i.get_map())
time.sleep(1)
print(i.change_map(5, 'n'))
print(i.get_map())

__author__ = 'Alexis'
