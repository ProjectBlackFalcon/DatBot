from Model.Pathfinder import PathFinder
import time

start = (-3, -6)
end = (5, 1)
pf = PathFinder(start, end, 15, 15, 2)

'''
maps_coords = pf.get_maps_coords()
maps = [pf.llf.coord_fetch_map(coord, 1) for coord in maps_coords]
pf.glue_maps(maps, (abs(end[1]-start[1])+1, abs(end[0]-start[0])+1))

start = time.time()
pf.adapt_shape_maps(pf.glued_maps)
pf.map_to_image(pf.adapted_maps, 1)
print(time.time()-start)
'''
maps_coords = pf.get_maps_coords()
maps = []
for coord in maps_coords:
    map_infos = pf.llf.coord_fetch_map(coord, pf.worldmap)
    if map_infos is not None:
        maps.append(map_infos)
    else:
        maps.append([[1 for i in range(14)] for j in range(40)])

pf.adapt_shape_maps(pf.glue_maps(maps, (abs(end[1]-start[1])+1, abs(end[0]-start[0])+1)))
pf.map_to_image(pf.adapted_maps, 10)

'''
pf.get_path()
pf.add_path_to_adapted_maps()
pf.add_map_change_coords_to_adapted_maps()
pf.map_to_image(pf.adapted_maps, 1)
print(pf.path_cells)
print(pf.get_map_change_cells())
'''
__author__ = 'Alexis'

