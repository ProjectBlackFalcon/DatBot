import numpy as np

shape = (1, 4)
arr = np.array([
    [['a', 'b'],
     ['c', 'd']],
    [['e', 'f'],
     ['g', 'h']],
    [['i', 'j'],
     ['k', 'l']],
    [['m', 'n'],
     ['o', 'p']]
])

# print(arr)
arr = arr.reshape((shape[0], shape[1], 2, 2))
out = np.concatenate([np.concatenate([map for map in arr[i]], axis=1) for i in range(shape[0])], axis=0)


print(out)
__author__ = 'Alexis'
