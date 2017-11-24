from Model.Pathfinder import PathFinder
from Model.Interface import Interface


class HighLevelFunctions:
    def __init__(self, bot_instance):
        self.interface = Interface(bot_instance)

    def goto(self, target_coord, target_cell=None, worldmap=1):
        current_map, current_cell, current_worldmap, map_id = self.interface.get_map()

        if current_worldmap != worldmap:
            # TODO manage worldmap changing
            raise Exception('Not in targeted worldmap')

        pf = PathFinder(current_map, target_coord, current_cell, target_cell, worldmap)
        path = pf.get_map_change_cells()
        for cell in path:
            if self.interface.move(cell):
                continue
            else:
                raise Exception('Interface returned false on move command')

        if target_cell is not None:
            self.interface.move(target_cell)

__author__ = 'Alexis'
