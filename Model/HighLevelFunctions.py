from Model.Pathfinder import PathFinder
from Model.Interface import Interface
from Model.LowLevelFunctions import LowLevelFunctions


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
        directions = pf.get_directions()
        for i in range(len(path)):
            if self.interface.change_map(path[i], directions[i]):
                continue
            else:
                raise Exception('Interface returned false on move command')

        if target_cell is not None:
            self.interface.move(target_cell)

    def harvest_map(self, harvest_only=None, do_not_harvest=None):
        map_resources = self.interface.get_map_resources()

        if harvest_only is not None:
            filtered_map_resources = {}
            for resource in harvest_only:
                if resource in map_resources.keys():
                    filtered_map_resources[resource] = map_resources[resource]
        else:
            filtered_map_resources = map_resources

        if do_not_harvest is not None:
            for resource in filtered_map_resources.keys():
                if resource in do_not_harvest:
                    del filtered_map_resources[resource]



__author__ = 'Alexis'
