from Model.Pathfinder import PathFinder
import time
import numpy as np
import json
import winsound


def generate_map_collage():
    maps_coords = pf.get_maps_coords()
    maps = []
    shape = (abs(end[1] - start[1]) + 1, abs(end[0] - start[0]) + 1)
    counter = 0
    for coord in maps_coords:
        map_infos = pf.llf.coord_fetch_map(coord, pf.worldmap)
        counter += 1
        print('{}/{}'.format(counter, shape[0]*shape[1]))
        if map_infos is not None and np.array(map_infos).shape == (40, 14):
            maps.append(map_infos)
        elif map_infos is not None and np.array(map_infos).shape != (40, 14):
            maps.append([[5]*14]*40)
        else:
            maps.append([[1] * 14] * 40)
    glued = pf.glue_maps(maps, shape)
    # print(glued)
    # print(pf.adapted_maps)
    pf.map_to_image(pf.adapt_shape_maps(glued), 1)


def generate_path():
    pf.get_path()
    pf.add_path_to_adapted_maps()
    pf.add_map_change_coords_to_adapted_maps()
    pf.map_to_image(pf.adapted_maps, 1)
    print(pf.path_cells)
    print(pf.get_map_change_cells())
    return(pf.get_map_change_cells())

lel = [(4, -19), (-5, -23), (-13, -28), (-3, -42), (-17, -47), (-32, -56), (-27, -36), (-20, -20), (-16, 1), (-25, 12), (-15, 25), (-26, 35)]

start = (4, -19)
end = (-5, -23)
worldmap = 1
pf = PathFinder(start, end, None, None, worldmap)
path = generate_path()


print('Done !')
winsound.PlaySound('..//Utils//sound.wav', winsound.SND_FILENAME)


__author__ = 'Alexis'

