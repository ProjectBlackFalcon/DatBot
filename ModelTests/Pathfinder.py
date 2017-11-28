from Model.Pathfinder import PathFinder
import time

start = (-35, -61)
end = (-23, -46)
pf = PathFinder(start, end, 15, None, 1)
maps_coords = pf.get_maps_coords()
maps = [pf.llf.coord_fetch_map(coord, 1) for coord in maps_coords]
pf.glue_maps(maps, (abs(end[1]-start[1])+1, abs(end[0]-start[0])+1))

start = time.time()
pf.adapt_shape_maps(pf.glued_maps)
print(time.time()-start)

'''
pf.get_path()
pf.get_map_change_coords()
pf.get_directions()
pf.add_path_to_glued_maps()
pf.add_map_change_ccords_to_glued_maps()
pf.map_to_image(pf.glued_maps, 10)
print(len(pf.path_cells))
print(pf.get_map_change_coords())
print(pf.get_directions())
'''
__author__ = 'Alexis'

