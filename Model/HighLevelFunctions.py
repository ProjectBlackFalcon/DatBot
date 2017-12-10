from Model.Pathfinder import PathFinder
from Model.Interface import Interface
from Model.LowLevelFunctions import LowLevelFunctions
import json


class HighLevelFunctions:
    def __init__(self):
        self.interfaces = []
        self.current_positions = []
        self.current_occupation = []
        self.credentials = []
        self.llf = LowLevelFunctions()
        self.selected_bot = None
        
    def new_bot(self, credentials):
        new_interface = Interface(len(self.interfaces))
        self.interfaces.append(new_interface)
        self.current_positions.append(None)
        self.current_occupation.append(None)
        self.credentials.append(credentials)
        if len(self.interfaces) == 1:
            self.selected_bot = 0
        return new_interface

    def goto(self, target_coord, target_cell=None, worldmap=1):
        current_map, current_cell, current_worldmap, map_id = self.interfaces[self.selected_bot].get_map()

        if self.llf.distance_coords(current_map, target_coord) > 3:
            self.current_positions[self.selected_bot] = (current_map, current_worldmap)
            self.current_occupation[self.selected_bot] = 'Moving to {}, worldmap {}'.format(target_coord, worldmap)
            self.update_db()

        if current_worldmap != worldmap:
            # Incarnam to Astrub
            if current_worldmap == 2 and worldmap == 1:
                self.goto((4, -3), worldmap=2)
                self.interfaces[self.selected_bot].go_to_astrub()
                current_map, current_cell, current_worldmap, map_id = self.interfaces[self.selected_bot].get_map()
            # Astrub to Incarnam
            elif current_worldmap == 1 and worldmap == 2:
                statue_map = self.llf.get_closest_statue(current_map)
                self.goto(statue_map)
                statue_cell = self.interfaces[self.selected_bot].get_class_statue_cell()[0]
                teleport_cell = self.llf.get_closest_walkable_neighbour_cell(statue_cell, current_cell, statue_map, current_worldmap)
                self.interfaces[self.selected_bot].move(teleport_cell)
                self.interfaces[self.selected_bot].go_to_incarnam()
                current_map, current_cell, current_worldmap, map_id = self.interfaces[self.selected_bot].get_map()

            # TODO manage worldmap changing
            else:
                raise Exception('Worldmap change not supported')

        if current_map == target_coord and current_cell == target_cell and worldmap == current_worldmap:
            return

        if current_map == target_coord and worldmap == current_worldmap and target_cell is not None:
            if self.interfaces[self.selected_bot].move(target_cell):
                return

        pf = PathFinder(current_map, target_coord, current_cell, target_cell, worldmap)
        path_directions = pf.get_map_change_cells()
        for i in range(len(path_directions)):
            if self.interfaces[self.selected_bot].change_map(path_directions[i][0], path_directions[i][1])[0]:
                continue
            else:
                raise Exception('Interface returned false on move command')

        if target_cell is not None:
            self.interfaces[self.selected_bot].move(target_cell)
        self.current_positions[self.selected_bot] = (target_coord, worldmap)

    def harvest_map(self, harvest_only=None, do_not_harvest=None):
        self.current_occupation[self.selected_bot] = 'Harvesting map'
        self.update_db()
        with open('..//Utils//resourcesIDs.json', 'r') as f:
            resources_ids = json.load(f)

        with open('..//Utils//resourcesLevels.json', 'r') as f:
            resources_levels = json.load(f)

        local_blacklist = []

        def harvest_one():
            map_resources_ids = self.interfaces[self.selected_bot].get_map_resources()
            map_coords, player_pos, worldmap, _ = self.interfaces[self.selected_bot].get_map()
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
            job_levels = self.interfaces[self.selected_bot].get_player_stats()[0]['Job']
            for resource, spots in filtered_map_resources2.items():
                if resource == "Eau" or resources_levels[resource][0] <= job_levels[resources_levels[resource][1]][0]:
                    filtered_map_resources3[resource] = spots

            harvestable = []
            harvestable_match_res_name = []
            for resource, spots in filtered_map_resources3.items():
                for spot in spots:
                    if spot[1] == 0:
                        harvestable.append(spot[0])
                        harvestable_match_res_name.append(resource)
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
                if not self.interfaces[self.selected_bot].move(selected_cell)[0]:
                    success = False
                resource_cell = self.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots])
                resource_name = harvestable_match_res_name[harvestable.index(resource_cell)]
                ret_val = self.interfaces[self.selected_bot].harvest_resource(resource_cell)
                if not ret_val[0]:
                    success = False
                else:
                    ret_val = ret_val[0], resource_name, ret_val[1], ret_val[2], ret_val[3]

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

        with open('..//Misc//HarvestLogs//HarvestLog_{}.txt'.format(self.selected_bot), 'a') as f:
            for item in harvest:
                f.write('ID : {}, Item : {}, Number : {}, Weight : {}\n'.format(item[0], item[1], item[2], int(item[3]*100/item[4])))
        if type(ret_val) is tuple and ret_val[3] == ret_val[4]:
            print('[Harvest] Full')
            return False
        else:
            print('[Harvest] Done')
            return True

    def harvest_path(self, path, number_of_loops=-1, harvest_only=None, do_not_harvest=None):
        n_loops = 0
        full = False
        while number_of_loops == -1 or n_loops < number_of_loops:
            n_loops += 1
            for tile, target_cell, worldmap in path:
                if not full:
                    self.goto(tile, target_cell=target_cell, worldmap=worldmap)
                    full = not self.harvest_map(harvest_only, do_not_harvest)
                else:
                    self.goto((4, -16))
                    self.drop_to_bank('all')
                    self.goto(tile, target_cell=target_cell, worldmap=worldmap)
                    full = not self.harvest_map(harvest_only, do_not_harvest)

    def drop_to_bank(self, item_id_list):
        self.current_occupation = 'Dropping to bank'
        self.update_db()
        bank_entrance, bank_exit = self.interfaces[self.selected_bot].get_bank_door_cell()
        if bank_entrance:
            self.interfaces[self.selected_bot].move(bank_entrance)
            self.interfaces[self.selected_bot].enter_bank()
            self.interfaces[self.selected_bot].open_bank()
            self.interfaces[self.selected_bot].drop_in_bank_list(item_id_list)
            self.interfaces[self.selected_bot].close_bank()
            self.interfaces[self.selected_bot].move(bank_exit)
        else:
            raise Exception('Not a map with a bank')

    def tresure_hunt(self):
        # Todo get hunt
        # Todo 
        pass

    def update_db(self):
        self.llf.update_db(
            self.selected_bot,
            self.credentials[self.selected_bot]['name'],
            self.current_occupation[self.selected_bot],
            self.current_positions[self.selected_bot][0],
            self.current_positions[self.selected_bot][1]
        )


__author__ = 'Alexis'
