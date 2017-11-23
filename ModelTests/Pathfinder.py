from Model.Pathfinder import PathFinder

pf = PathFinder((-2, -22), (2, -18), 15, 500, 1)
pf.get_path()
pf.get_map_change_coords()
pf.add_path_to_glued_maps()
pf.add_map_change_ccords_to_glued_maps()
pf.map_to_image(pf.glued_maps, 10)
__author__ = 'Alexis'

