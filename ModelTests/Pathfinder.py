from Model.Pathfinder import PathFinder

pf = PathFinder((-6, -1), (-10, 1), 15, None, 1)


pf.get_path()
pf.get_map_change_coords()
pf.get_directions()
pf.add_path_to_glued_maps()
pf.add_map_change_ccords_to_glued_maps()
pf.map_to_image(pf.glued_maps, 10)
print(len(pf.path_cells))
print(pf.get_map_change_coords())
print(pf.get_directions())
__author__ = 'Alexis'

