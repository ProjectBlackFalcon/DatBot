from Model.Pathfinder import PathFinder
from Model.Interface import Interface
from Model.LowLevelFunctions import LowLevelFunctions
import json


class HighLevelFunctions:
    def __init__(self, bot_instance):
        self.interface = Interface(bot_instance, headless=True)
        self.llf = LowLevelFunctions()

    def goto(self, target_coord, target_cell=None, worldmap=1):
        current_map, current_cell, current_worldmap, map_id = self.interface.get_map()

        if current_worldmap != worldmap:
            # TODO manage worldmap changing
            raise Exception('Not in targeted worldmap')

        pf = PathFinder(current_map, target_coord, current_cell, target_cell, worldmap)
        path_directions = pf.get_map_change_cells()
        for i in range(len(path_directions)):
            if self.interface.change_map(path_directions[i][0], path_directions[i][1]):
                continue
            else:
                raise Exception('Interface returned false on move command')

        if target_cell is not None:
            self.interface.move(target_cell)

    def harvest_map(self, harvest_only=None, do_not_harvest=None):
        with open('..//Utils//resourcesIDs.json', 'r') as f:
            resources_ids = json.load(f)

        with open('..//Utils//resourcesLevels.json', 'r') as f:
            resources_levels = json.load(f)

        def harvest_one():
            map_resources_ids = self.interface.get_map_resources()
            map_coords, player_pos, worldmap, _ = self.interface.get_map()
            map_resources = {}
            for cell_id, res_id, status in map_resources_ids:
                if str(res_id) in resources_ids.keys():
                    if resources_ids[str(res_id)] in map_resources.keys():
                        map_resources[resources_ids[str(res_id)]].append((cell_id, status))
                    else:
                        map_resources[resources_ids[str(res_id)]] = [(cell_id, status)]
                else:
                    with open('..//Utils//unknownResourseID.txt', 'a') as f:
                        f.write('Map : {}, ID : {}, Cell : {}'.format(player_pos, res_id, cell_id))
            print('[Harvest] map_resources : {}'.format(map_resources))

            if harvest_only is not None:
                filtered_map_resources = {}
                for resource in harvest_only:
                    if resource in map_resources.keys():
                        filtered_map_resources[resource] = map_resources[resource]
            else:
                filtered_map_resources = map_resources

            filtered_map_resources2 = {}
            if do_not_harvest is not None:
                for resource in filtered_map_resources.keys():
                    if resource not in do_not_harvest:
                        filtered_map_resources2[resource] = filtered_map_resources[resource]
            else:
                filtered_map_resources2 = filtered_map_resources
            print('[Harvest] filtered_map_resources2 : {}'.format(filtered_map_resources2))

            harvestable = []
            for resource, spots in filtered_map_resources2.items():
                for spot in spots:
                    if spot[1] == 0:
                        harvestable.append(spot[0])
            print('[Harvest] harvestable : {}'.format(harvestable))

            # TODO level filtering

            if not harvestable:
                return False

            harvest_spots = []
            for cell in harvestable:
                neighbour_cell = self.llf.get_closest_walkable_neighbour_cell(cell, player_pos, map_coords, worldmap)
                if neighbour_cell:
                    harvest_spots.append((neighbour_cell, cell))
                else:
                    harvest_spots.append((self.llf.get_closest_walkable_cell(cell, map_coords, worldmap), cell))
            print('[Harvest] harvest spot : {}'.format(harvest_spots))

            if harvest_spots:
                selected_cell = self.llf.closest_cell(player_pos, [spot[0] for spot in harvest_spots])
                self.interface.move(selected_cell)
                ret_val = self.interface.harvest_resource(self.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots]))
            return ret_val

        ret_val = harvest_one()
        while ret_val:
            ret_val = harvest_one()
        print('[Harvest] Done')

__author__ = 'Alexis'
