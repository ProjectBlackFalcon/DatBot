from Model.Pathfinder import PathFinder
from Model.LowLevelFunctions import LowLevelFunctions
import json


class HighLevelFunctions:
    def __init__(self, bot):
        self.bot = bot
        self.llf = LowLevelFunctions()

    def goto(self, target_coord, target_cell=None, worldmap=1):
        current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if self.llf.distance_coords(current_map, target_coord) > 3:
            self.bot.position = (current_map, current_worldmap)
            self.bot.occupation = 'Moving to {}, worldmap {}'.format(target_coord, worldmap)
            self.update_db()

        if current_worldmap != worldmap:
            # Incarnam to Astrub
            if current_worldmap == 2 and worldmap == 1:
                self.goto((4, -3), worldmap=2)
                self.bot.interface.go_to_astrub()
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
            # Astrub to Incarnam
            elif current_worldmap == 1 and worldmap == 2:
                statue_map = tuple(self.llf.get_closest_statue(current_map))
                while current_map != statue_map:
                    self.goto(statue_map)
                    current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                statue_cell = self.bot.interface.get_class_statue_cell()[0]
                teleport_cell = self.llf.get_closest_walkable_neighbour_cell(statue_cell, current_cell, statue_map, current_worldmap)
                self.bot.interface.move(teleport_cell)
                self.bot.interface.go_to_incarnam()
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

            # TODO manage worldmap changing
            else:
                raise Exception('Worldmap change not supported')

        closest_zaap = self.llf.get_closest_known_zaap(self.bot.credentials['name'], target_coord)
        distance_zaap_target = self.llf.distance_coords(closest_zaap, target_coord)
        if worldmap == current_worldmap and self.llf.distance_coords(current_map, target_coord) > distance_zaap_target+5:
            if self.bot.interface.enter_heavenbag():
                self.bot.interface.use_zaap(closest_zaap)
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if current_map == target_coord and current_cell == target_cell and worldmap == current_worldmap:
            return

        if current_map == target_coord and worldmap == current_worldmap and target_cell is not None:
            if self.bot.interface.move(target_cell):
                return

        pf = PathFinder(current_map, target_coord, current_cell, target_cell, worldmap)
        path_directions = pf.get_map_change_cells()
        for i in range(len(path_directions)):
            if self.bot.interface.change_map(path_directions[i][0], path_directions[i][1])[0]:
                self.bot.position = self.bot.interface.get_map()[0]
                self.llf.add_discovered_zaap(self.bot.credentials['name'], self.bot.position)
            else:
                raise Exception('Interface returned false on move command')

        if target_cell is not None:
            self.bot.interface.move(target_cell)
        self.bot.position = (target_coord, worldmap)

    def discover_zaaps(self):
        with open('zaapDiscoveryPath.json', 'r') as f:
            paths = json.load(f)
        for path_directions in paths:
            for i in range(len(path_directions)):
                if self.bot.interface.change_map(path_directions[i][0], path_directions[i][1])[0]:
                    self.bot.position = self.bot.interface.get_map()[0]
                    self.llf.add_discovered_zaap(self.bot.credentials['name'], self.bot.position)
                else:
                    raise Exception('Interface returned false on move command')

    def harvest_map(self, harvest_only=None, do_not_harvest=None):
        self.bot.occupation = 'Harvesting map'
        self.update_db()
        with open('..//Utils//resourcesIDs.json', 'r') as f:
            resources_ids = json.load(f)

        with open('..//Utils//resourcesLevels.json', 'r') as f:
            resources_levels = json.load(f)

        local_blacklist = []

        def harvest_one():
            map_resources_ids = self.bot.interface.get_map_resources()
            map_coords, player_pos, worldmap, _ = self.bot.interface.get_map()
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
            job_levels = self.bot.interface.get_player_stats()[0]['Job']
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
            # print('Harvestable :', harvestable)

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
                if not self.bot.interface.move(selected_cell)[0]:
                    success = False
                resource_cell = self.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots])
                resource_name = harvestable_match_res_name[harvestable.index(resource_cell)]
                ret_val = self.bot.interface.harvest_resource(resource_cell)
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

        with open('..//Misc//HarvestLogs//HarvestLog_{}.txt'.format(self.bot), 'a') as f:
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

    def drop_to_bank(self, item_id_list='all'):
        self.bot.occupation = 'Dropping to bank'
        self.update_db()
        bank_entrance, bank_exit = self.bot.interface.get_bank_door_cell()
        if bank_entrance:
            self.bot.interface.move(bank_entrance)
            self.bot.interface.enter_bank()
            self.bot.interface.open_bank()
            self.bot.interface.drop_in_bank_list(item_id_list)
            self.bot.interface.close_bank()
            self.bot.interface.move(bank_exit)
        else:
            raise Exception('Not a map with a bank')

    def tresure_hunt(self, level='max'):
        def get_hunt(level):
            self.bot.occupation = 'Getting a new hunt'
            self.update_db()
            self.goto((-25, -36))
            hall_entrance, hall_exit = self.bot.interface.get_hunting_hall_door_cell()
            self.bot.interface.move(hall_entrance)
            self.bot.interface.enter_hunting_hall()
            self.bot.interface.move(304)  # The cell used to pick up a treasure hunt
            self.bot.interface.get_new_hunt(level)
            self.bot.interface.exit_hunting_hall()

        if level == 'max':
            level = self.bot.interface.get_player_stats()[0]['Level']
        level = int(level/20)*20
        get_hunt(level)

        hunt_state = 'start'
        while not hunt_state == 'done':
            if hunt_state == 'start':
                hunt_start = self.bot.interface.get_hunt_start()
                clue_pos = hunt_start
                hunt_state = 'in progress'

            elif hunt_state == 'in progress':
                self.goto(clue_pos)
                clue, direction = self.bot.interface.get_hunt_clue()
                next_clue_pos = self.llf.get_next_clue_pos(clue, clue_pos, direction)
        # Todo no more clues, next step

    def update_db(self):
        try:
            print('[Database] Uploading {}, {}, {}, {}, {}'.format(self.bot.id, self.bot.credentials['name'], self.bot.occupation, self.bot.position[0], self.bot.position[1]))
            self.llf.update_db(
                self.bot,
                self.bot.credentials['name'],
                self.bot.occupation[self.bot],
                self.bot.positions[0],
                self.bot.positions[1]
            )
        except Exception:
            pass


__author__ = 'Alexis'
