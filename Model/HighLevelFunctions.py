from Model.Pathfinder import PathFinder
from Model.Interface import Interface
from Model.LowLevelFunctions import LowLevelFunctions
import json


class HighLevelFunctions:
    def __init__(self, bot_instance):
        self.bot_instance = bot_instance
        self.interface = Interface(bot_instance, headless=True)
        self.llf = LowLevelFunctions()

    def goto(self, target_coord, target_cell=None, worldmap=1):
        current_map, current_cell, current_worldmap, map_id = self.interface.get_map()

        if current_worldmap != worldmap:
            # Incarnam to Astrub
            if current_worldmap == 2 and worldmap == 1:
                self.goto((4, -3), worldmap=2)
                self.interface.go_to_astrub()
                current_map, current_cell, current_worldmap, map_id = self.interface.get_map()
            # Astrub to Incarnam
            elif current_worldmap == 1 and worldmap == 2:
                statue_map = self.llf.get_closest_statue(current_map)
                self.goto(statue_map)
                statue_cell = self.interface.get_class_statue_cell()
                self.interface.move(statue_cell)
                self.interface.go_to_incarnam()
                current_map, current_cell, current_worldmap, map_id = self.interface.get_map()

            # TODO manage worldmap changing
            else:
                raise Exception('Worldmap change not supported')

        if current_map == target_coord and current_cell == target_cell and worldmap == current_worldmap:
            return

        if current_map == target_coord and worldmap == current_worldmap and target_cell is not None:
            if self.interface.move(target_cell):
                return

        pf = PathFinder(current_map, target_coord, current_cell, target_cell, worldmap)
        path_directions = pf.get_map_change_cells()
        for i in range(len(path_directions)):
            if self.interface.change_map(path_directions[i][0], path_directions[i][1])[0]:
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

        local_blacklist = []

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
                    with open('..//Utils//unknownResourceID.txt', 'a') as f:
                        f.write('Map : {}, ID : {}, Cell : {}\n'.format(map_coords, res_id, cell_id))
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

            filtered_map_resources3 = {}
            job_levels = self.interface.get_player_stats()[0]['Job']
            for resource, spots in filtered_map_resources2.items():
                if resource == "Eau" or resources_levels[resource][0] <= job_levels[resources_levels[resource][1]][0]:
                    filtered_map_resources3[resource] = spots

            harvestable = []
            for resource, spots in filtered_map_resources3.items():
                for spot in spots:
                    if spot[1] == 0:
                        harvestable.append(spot[0])
            print('[Harvest] harvestable : {}'.format(harvestable))

            harvestable = list(set(harvestable)-set(local_blacklist))
            print('Harvestable :', harvestable)

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
                success = True
                selected_cell = self.llf.closest_cell(player_pos, [spot[0] for spot in harvest_spots])
                if not self.interface.move(selected_cell)[0]:
                    success = False
                ret_val = self.interface.harvest_resource(self.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots]))
                if not ret_val[0]:
                    success = False

                if not success:
                    inacessible_res = self.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots])
                    local_blacklist.append(inacessible_res)
                    print('Black List : ', local_blacklist)
                    return -1
                return ret_val
            return False

        harvest = []
        full = False
        ret_val = harvest_one()
        if type(ret_val) is tuple:
            harvest.append(ret_val)
            full = False if ret_val[3] < ret_val[4] else True

        while ret_val and not full:
            ret_val = harvest_one()
            if type(ret_val) is tuple:
                full = False if ret_val[3] < ret_val[4] else True
                harvest.append(ret_val)

        with open('..//Misc//HarvestLogs//HarvestLog_{}.txt'.format(self.bot_instance), 'a') as f:
            for item in harvest:
                f.write('ID : {}, Item : {}, Number : {}, Weight : {}\n'.format(item[0], item[1], item[2], int(item[3]*100/item[4])))
        if type(ret_val) is tuple and ret_val[3] == ret_val[4]:
            print('[Harvest] Full')
        else:
            print('[Harvest] Done')

    def get_inventory(self):
        pass

__author__ = 'Alexis'
