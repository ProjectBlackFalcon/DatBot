from Model.Pathfinder import PathFinder
import time

start = (-10, 1)
end = (-7, -1)
pf = PathFinder(start, end, 15, None, 1)

'''
maps_coords = pf.get_maps_coords()
maps = [pf.llf.coord_fetch_map(coord, 1) for coord in maps_coords]
pf.glue_maps(maps, (abs(end[1]-start[1])+1, abs(end[0]-start[0])+1))

start = time.time()
pf.adapt_shape_maps(pf.glued_maps)
pf.map_to_image(pf.adapted_maps, 1)
print(time.time()-start)
'''

pf.get_path()
pf.add_path_to_adapted_maps()
pf.add_map_change_coords_to_adapted_maps()
pf.map_to_image(pf.adapted_maps, 1)
print(pf.path_cells)
print(pf.get_map_change_cells())

__author__ = 'Alexis'

