import pyximport; pyximport.install()
from Pathfinder import PathFinder
from Hunt import Hunt
from DD import DD
import json
import time
import datetime
import traceback
import pandas as pd
from math import ceil


class HighLevelFunctions:
    def __init__(self, bot):
        self.bot = bot

    def goto(self, target_coord, target_cell=None, worldmap=1, harvest=False, forbid_zaaps=False):
        current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if current_worldmap != worldmap:
            # Incarnam to Astrub
            if current_worldmap == 2 and worldmap == 1:
                self.goto((4, -3), worldmap=2)
                self.bot.interface.go_to_astrub()
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
            # Astrub to Incarnam
            elif current_worldmap == 1 and worldmap == 2:
                gate_map = (6, -19)
                self.goto(gate_map, target_cell=397)
                self.bot.interface.go_to_incarnam()
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

            elif list(current_map) == [-25, -36] and current_worldmap == -1:
                # bot is in hunting hall
                self.bot.interface.exit_hunting_hall()
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

            elif current_worldmap == -1:
                closest_zaap = self.bot.llf.get_closest_known_zaap(self.bot.credentials['name'], target_coord)
                if closest_zaap is not None:
                    if self.bot.interface.enter_heavenbag()[0]:
                        tries = 0
                        while tuple(current_map) != tuple(closest_zaap) and tries < 5:
                            self.bot.interface.use_zaap(closest_zaap)
                            current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                            time.sleep(2)
                            tries += 1
                        if tuple(current_map) != tuple(closest_zaap):
                            raise RuntimeError('Unable to use Zaap to go to {}'.format(closest_zaap))
                    else:
                        if self.bot.characteristics.level < 10:
                            raise RuntimeError('Bot is not level 10 an can not enter heavenbag')
                        else:
                            raise RuntimeError('Unable to enter heavenbag to go from {}, worldmap -1 to {}'.format(current_map, closest_zaap))

            # TODO manage more worldmap changing
            else:
                raise RuntimeError('Worldmap change not supported')

        closest_zaap = self.bot.llf.get_closest_known_zaap(self.bot.credentials['name'], target_coord)
        if closest_zaap is not None and not forbid_zaaps:
            distance_zaap_target = self.bot.llf.distance_coords(closest_zaap, tuple(target_coord))
            if worldmap == current_worldmap and self.bot.llf.distance_coords(current_map, tuple(target_coord)) > distance_zaap_target+5:
                if self.bot.characteristics.level > 10 or self.bot.interface.enter_heavenbag()[0]:
                    self.bot.interface.use_zaap(closest_zaap)
                    current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                    while tuple(current_map) != tuple(closest_zaap):
                        current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                        self.bot.interface.enter_heavenbag()
                        self.bot.interface.use_zaap(closest_zaap)
                        time.sleep(2)
                else:
                    closest_zaap_2 = self.bot.llf.get_closest_known_zaap(self.bot.credentials['name'], self.bot.position[0])
                    self.goto(closest_zaap_2, forbid_zaaps=True)
                    if self.bot.characteristics.level < 10 or not self.bot.interface.enter_heavenbag()[0]:
                        zaap_cell = self.bot.interface.get_zaap_cell()[0]
                        self.bot.interface.move(self.bot.llf.get_closest_walkable_neighbour_cell(zaap_cell, current_cell, self.bot.position[0], self.bot.position[1]))
                    self.bot.interface.use_zaap(closest_zaap)
                    current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if list(current_map) not in self.bot.resources.brak_maps and list(target_coord) in self.bot.resources.brak_maps:
            # Bot needs to enter brak
            disc_zaaps = self.bot.llf.get_discovered_zaaps(self.bot.credentials['name'])
            if [-26, 35] in disc_zaaps and self.bot.interface.enter_heavenbag()[0]:
                self.bot.interface.use_zaap((-26, 35))
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
        if list(current_map) in self.bot.resources.brak_maps and list(target_coord) not in self.bot.resources.brak_maps:
            # Bot needs to exit brak
            if list(target_coord) in self.bot.resources.brak_east_maps:
                self.goto((-20, 34))
                self.bot.interface.change_map(307, 'e')
            elif list(target_coord) in self.bot.resources.brak_north_maps:
                self.goto((-26, 31), target_cell=110)
                self.bot.interface.exit_brak_north()
            current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if list(current_map) in self.bot.resources.west_dd_territory_maps and list(target_coord) in self.bot.resources.dd_territory_maps:
            # Bot needs to enter dd territory from west
            self.goto((-23, -1), target_cell=387)
            self.bot.interface.enter_dd_territory()
            current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
        if list(current_map) in self.bot.resources.dd_territory_maps and list(target_coord) in self.bot.resources.west_dd_territory_maps:
            # Bot needs to exit dd territory to the west
            self.goto((-22, -1))
            self.bot.interface.change_map(294, 'w')
            current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if list(current_map) not in self.bot.resources.castle_maps and list(target_coord) in self.bot.resources.castle_maps:
            # Bot needs to enter the castle
            disc_zaaps = self.bot.llf.get_discovered_zaaps(self.bot.credentials['name'])
            if [3, -5] in disc_zaaps and self.bot.interface.enter_heavenbag()[0]:
                self.bot.interface.use_zaap((3, -5))
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
        if list(current_map) in self.bot.resources.castle_maps and list(target_coord) not in self.bot.resources.castle_maps:
            # Bot needs to exit the castle through the northern gate
            if target_coord[1] <= current_map[1]:
                self.goto((4, -8))
                self.bot.interface.change_map(140, 'w')
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if list(current_map) not in self.bot.resources.bwork_maps and list(target_coord) in self.bot.resources.bwork_maps:
            # Bot needs to enter bwork village
            self.goto((-1, 8), target_cell=383)
            self.bot.interface.enter_bwork()
            current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
        if list(current_map) in self.bot.resources.bwork_maps and list(target_coord) not in self.bot.resources.bwork_maps:
            # Bot needs to exit bwork village
            self.goto((-2, 8), target_cell=260)
            self.bot.interface.exit_bwork()
            current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        if current_map == target_coord and current_cell == target_cell and worldmap == current_worldmap:
            return

        if current_map == target_coord and worldmap == current_worldmap and target_cell is not None:
            if self.bot.interface.move(target_cell):
                return

        pf = PathFinder(self.bot, current_map, target_coord, current_cell, target_cell, worldmap)
        path_directions = pf.get_map_change_cells()
        for i in range(len(path_directions)):
            if self.bot.interface.change_map(path_directions[i][0], path_directions[i][1])[0]:
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                self.bot.position = current_map, current_worldmap
                if current_worldmap == 1:
                    self.bot.llf.add_discovered_zaap(self.bot.credentials['name'], self.bot.position)
                if harvest:
                    self.harvest_map()
            else:
                raise ValueError('Interface returned false on move command. Position : {}, Cell : {}, Direction : {}'.format(self.bot.position, path_directions[i][0], path_directions[i][1]))

        if tuple(current_map) != tuple(target_coord):
            self.goto(target_coord, target_cell, worldmap)

        if target_cell is not None:
            self.bot.interface.move(target_cell)
        self.bot.position = (target_coord, worldmap)

    def discover_zaaps(self):
        if not self.bot.connected:
            self.bot.interface.connect()
        self.bot.occupation = 'Discovering Zaaps'
        self.update_db()
        closest_unk_zaap = self.bot.llf.get_closest_unknown_zaap(self.bot.credentials['name'], self.bot.position[0])
        while closest_unk_zaap:
            self.goto(closest_unk_zaap)
            self.bot.llf.add_discovered_zaap(self.bot.credentials['name'], self.bot.position[0])
            closest_unk_zaap = self.bot.llf.get_closest_unknown_zaap(self.bot.credentials['name'], self.bot.position[0])

    def harvest_map(self, harvest_only=None, do_not_harvest=None):
        if self.bot.characteristics.weight_max - self.bot.characteristics.weight < 200:
            return False
        self.bot.occupation = 'Harvesting map'
        self.update_db()

        local_blacklist = []

        def harvest_one():
            map_resources_ids = self.bot.interface.get_map_resources()
            map_coords, player_pos, worldmap, _ = self.bot.interface.get_map()
            map_resources = {}
            for cell_id, res_id, status in map_resources_ids:
                if str(res_id) in self.bot.resources.resources_ids.keys():
                    if self.bot.resources.resources_ids[str(res_id)] in map_resources.keys():
                        map_resources[self.bot.resources.resources_ids[str(res_id)]].append((cell_id, status))
                    else:
                        map_resources[self.bot.resources.resources_ids[str(res_id)]] = [(cell_id, status)]
                else:
                    with open('../Utils/unknownResourceID.txt', 'a') as f:
                        f.write('Map : {}, ID : {}, Cell : {}\n'.format(map_coords, res_id, cell_id))
            # print('[Harvest] map_resources : {}'.format(map_resources))

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
            # print('[Harvest] filtered_map_resources2 : {}'.format(filtered_map_resources2))

            filtered_map_resources3 = {}
            job_levels = self.bot.characteristics.jobs
            for resource, spots in filtered_map_resources2.items():
                if resource == "Eau" or self.bot.resources.resources_levels[resource][0] <= job_levels[self.bot.resources.resources_levels[resource][1]][0]:
                    filtered_map_resources3[resource] = spots

            harvestable = []
            harvestable_match_res_name = []
            for resource, spots in filtered_map_resources3.items():
                for spot in spots:
                    if spot[1] == 0:
                        harvestable.append(spot[0])
                        harvestable_match_res_name.append(resource)
            # print('[Harvest] harvestable : {}'.format(harvestable))

            harvestable = list(set(harvestable)-set(local_blacklist))
            # print('Harvestable :', harvestable)

            if not harvestable:
                return False

            harvest_spots = []
            for cell in harvestable:
                neighbour_cell = self.bot.llf.get_closest_walkable_neighbour_cell(cell, player_pos, map_coords, worldmap)
                if neighbour_cell:
                    harvest_spots.append((neighbour_cell, cell))
                else:
                    harvest_spots.append((self.bot.llf.get_closest_walkable_cell(cell, map_coords, worldmap), cell))
            # print('[Harvest] harvest spot : {}'.format(harvest_spots))

            if harvest_spots:
                success = True
                selected_cell = self.bot.llf.closest_cell(player_pos, [spot[0] for spot in harvest_spots])
                if not self.bot.interface.move(selected_cell)[0]:
                    success = False
                    # TODO
                resource_cell = self.bot.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots])
                resource_name = harvestable_match_res_name[harvestable.index(resource_cell)]
                ret_val = self.bot.interface.harvest_resource(resource_cell)
                if len(ret_val) == 1:
                    success = False
                else:
                    ret_val = ret_val[0], resource_name, ret_val[1], ret_val[2], ret_val[3]

                if not success:
                    inacessible_res = self.bot.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots])
                    local_blacklist.append(inacessible_res)
                    # print('Black List : ', local_blacklist)
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

        with open('../Utils/HarvestLog_{}.txt'.format(self.bot.id), 'a') as f:
            for item in harvest:
                f.write('ID : {}, Item : {}, Number : {}, Weight : {}\n'.format(item[0], item[1], item[2], int(item[3]*100/item[4])))
        if type(ret_val) is tuple and ret_val[3]+5 >= ret_val[4]:
            self.bot.llf.log(self.bot, '[Harvest {}] Full'.format(self.bot.id))
            return False
        else:
            self.bot.llf.log(self.bot, '[Harvest {}] Done'.format(self.bot.id))
            return True

    def harvest_path(self, duration_minutes, path=None, harvest_only=None, do_not_harvest=None, sell=False):
        duration = duration_minutes * 60
        start = time.time()
        full = False
        path = self.bot.llf.fetch_harvest_path(self.bot.credentials['name']) if path is None else path
        while time.time() - start < duration:
            for tile, target_cell, worldmap in path:
                if time.time() - start > duration:
                    continue
                if not full:
                    try:
                        self.goto(tile, target_cell=target_cell, worldmap=worldmap)
                    except Exception:
                        self.bot.llf.log(self.bot, str(traceback.format_exc()))
                    full = not self.harvest_map(harvest_only, do_not_harvest)
                else:
                    self.drop_to_bank('all', withdraw_items_to_sell=sell)
                    if sell:
                        self.sell_all()
                    self.goto(tile, target_cell=target_cell, worldmap=worldmap)
                    full = not self.harvest_map(harvest_only, do_not_harvest)

    def withdraw_items_to_sell_from_bank(self, player_stats, bank_contents):
        self.bot.occupation = 'Withdrawing items from bank'
        self.update_db()
        if self.bot.credentials['name'] in self.bot.resources.items_to_sell.keys():
            items_to_sell = self.bot.resources.items_to_sell[self.bot.credentials['name']]
        else:
            items_to_sell = self.bot.resources.items_to_sell['Default']
        item_to_sell_ids = [int(key) for hdv_name in items_to_sell.keys() for key in items_to_sell[hdv_name]]
        item_to_sell_batch_size = [items_to_sell[hdv_name][key]['quantity'] for hdv_name in items_to_sell.keys() for key in items_to_sell[hdv_name]]
        for item_id in item_to_sell_ids:
            unique_id = self.bot.llf.get_inventory_id(bank_contents['Items'], item_id)
            number = self.bot.llf.get_number_of_item_in_inventory(bank_contents['Items'], item_id)
            weight = self.bot.llf.get_weight_of_item_in_inventory(bank_contents['Items'], item_id)
            inv_space = player_stats['WeightMax'] - player_stats['Weight']
            quantity_to_withdraw = min(number, int(inv_space / weight)) if weight else number
            batch_size = item_to_sell_batch_size[item_to_sell_ids.index(item_id)]
            round_quantity_to_withdraw = quantity_to_withdraw // batch_size * batch_size
            if round_quantity_to_withdraw:
                player_stats, bank_contents = self.bot.interface.get_from_bank_unique(unique_id, round_quantity_to_withdraw)

    def drop_to_bank(self, item_id_list='all', withdraw_items_to_sell=False):
        self.bot.occupation = 'Dropping to bank'
        self.update_db()
        if not tuple(self.bot.position[0]) == (4, -18):
            self.goto((4, -18))
        if self.bot.interface.enter_bank()[0]:
            self.bot.interface.open_bank()
            player_stats, bank_contents = self.bot.interface.drop_in_bank_list(item_id_list)
            player_stats, bank_contents = self.get_future_stuff_from_bank(bank_contents)
            if withdraw_items_to_sell:
                self.withdraw_items_to_sell_from_bank(player_stats, bank_contents)
            self.bot.interface.get_kamas_from_bank('all')
            self.bot.interface.close_bank()
            self.bot.interface.exit_bank()
            return player_stats, bank_contents
        else:
            raise Exception('Not a map with a bank')

    def get_future_stuff_from_bank(self, bank_contents=None):
        stuff_level = int(self.bot.characteristics.level / 20) * 20
        future_preferred_stuffs = [self.bot.resources.preferred_stuffs[level] for level in self.bot.resources.preferred_stuffs.keys() if int(level) >= stuff_level]
        ids = set([])
        for stuff in future_preferred_stuffs:
            for item in stuff:
                ids.add(item['Id'])
        items_to_retrieve = []
        for item in bank_contents['Items']:
            if item[1] in ids:
                items_to_retrieve.append(item[2])
        return self.bot.interface.get_from_bank_list(items_to_retrieve)

    def tresure_hunt(self, level='max', harvest=False):
        def get_hunt(level):
            self.bot.occupation = 'Getting a new hunt'
            self.update_db()
            self.goto((-25, -36), worldmap=1)
            door = self.bot.interface.get_hunting_hall_door_cell()[0]
            self.bot.interface.move(self.bot.llf.get_closest_walkable_cell(door, self.bot.position[0], self.bot.position[1]))
            self.bot.interface.enter_hunting_hall()
            while not self.bot.interface.get_new_hunt(level)[0]:
                self.bot.llf.log(self.bot, '[Treasure Hunt {}] Getting new hunt'.format(self.bot.id))
                time.sleep(30)
            self.bot.interface.exit_hunting_hall()

        if level == 'max':
            level = self.bot.characteristics.level
        level = int(level/20)*20

        if not self.bot.interface.hunt_is_active()[0]:
            get_hunt(level)
        hunt_start = self.bot.interface.get_hunt_start()[0]
        hunt = Hunt(self.bot, level, hunt_start)
        self.goto(hunt_start)

        self.bot.occupation = 'Treasure Hunting'
        self.update_db()

        while self.bot.interface.get_steps_left()[0] and not hunt.error:
            clues_left = self.bot.interface.get_clues_left()[0]
            hunt.add_new_step(clues_left)
            while clues_left and not hunt.error:
                clue, direction = self.bot.interface.get_hunt_clue()
                hunt.current_step().add_new_clue(clue, self.bot.position[0], direction)
                hunt.added_clue = False
                destination = None
                if 'Phorreur' in clue:
                    while not (self.bot.interface.check_for_phorror()[0] == clue) and not hunt.error:
                        direction_coords = [(0, -1), (0, 1), (-1, 0), (1, 0)][['n', 's', 'w', 'e'].index(direction)]
                        try:
                            destination = [sum(x) for x in zip(self.bot.position[0], direction_coords)]
                            self.goto(destination, harvest=harvest)
                        except Exception as e:
                            with open('../Utils/HuntErrorsLog.txt', 'a') as f:
                                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                                f.write(traceback.format_exc())
                            with open('../Utils/HuntErrorsLogBrief.txt', 'a') as f:
                                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                                f.write('Could not go to {} from {} to find {}'.format(destination, self.bot.position, clue))
                            hunt.error = True
                            hunt.reason = 'phorror goto failed'
                    if not hunt.error:
                        hunt.current_clue().guessed_pos = self.bot.position[0]
                else:
                    clue = clue.lower()
                    try:
                        clue_pos = self.bot.llf.get_next_clue_pos(clue, self.bot.position[0], direction)
                        hunt.current_clue().guessed_pos = clue_pos
                        self.goto(clue_pos, harvest=harvest)
                    except Exception as e:
                        with open('../Utils/HuntErrorsLog.txt', 'a') as f:
                            f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                            f.write(traceback.format_exc())

                        with open('../Utils/HuntErrorsLogBrief.txt', 'a') as f:
                            f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                            f.write(e.args[0])

                        if 'Non existing clue' in e.args[0]:
                            self.bot.llf.log(self.bot, '[Treasure Hunt {}] Clue is not referenced, trying to get to it...'.format(self.bot.id))
                            found = False
                            self.bot.interface.validate_hunt_step()
                            clues_left = self.bot.interface.get_clues_left()[0]
                            clue, direction = self.bot.interface.get_hunt_clue()
                            last_valid_clue_pos = self.bot.interface.get_hunt_start()[0]
                            self.goto(last_valid_clue_pos)
                            while not found and self.bot.interface.hunt_is_active()[0]:
                                direction_coords = [(0, -1), (0, 1), (-1, 0), (1, 0)][['n', 's', 'w', 'e'].index(direction)]
                                destination = [sum(x) for x in zip(self.bot.position[0], direction_coords)]
                                try:
                                    self.goto(destination, harvest=harvest)
                                except Exception as e:
                                    self.bot.llf.log(self.bot, '[Treasure Hunt {}] Failed to get to clue. It might have been wrongly blacklisted in TresureHuntNoClues.json'.format(self.bot.id))
                                    with open('../Utils/HuntErrorsLog.txt', 'a') as f:
                                        f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                                        f.write(traceback.format_exc())

                                    with open('../Utils/HuntErrorsLogBrief.txt', 'a') as f:
                                        f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                                        f.write(e.args[0])
                                    break

                                if not (self.bot.position[0] in hunt.get_no_clue_list(clue)):
                                    self.bot.interface.validate_hunt_clue()
                                    step_valid = self.bot.interface.validate_hunt_step()[0]
                                    new_clues_left = self.bot.interface.get_clues_left()[0]
                                    if step_valid or (new_clues_left != clues_left and new_clues_left):
                                        found = True
                                        hunt.added_clue = True
                                        hunt.add_to_clue_list(clue, self.bot.position)
                                        self.bot.llf.log(self.bot, '[Treasure Hunt {}] Discovered clue'.format(self.bot.id))
                                    elif not step_valid and self.bot.interface.hunt_is_active()[0]:
                                        hunt.add_to_no_clue_list(clue, self.bot.position)

                            if not found:
                                hunt.error = True
                                hunt.reason = e.args[0]
                        else:
                            hunt.error = True
                            hunt.reason = 'Goto failed'

                if not hunt.error and not hunt.added_clue and not self.bot.interface.validate_hunt_clue()[0]:
                    clue, direction = self.bot.interface.get_hunt_clue()
                    last_valid_clue_pos = self.bot.interface.get_hunt_start()[0]
                    if 'Phorreur' not in clue:
                        clue_pos = self.bot.llf.get_next_clue_pos(clue, last_valid_clue_pos, direction)
                    with open('../Utils/HuntErrorsLogBrief.txt', 'a') as f:
                        f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                        f.write('Failed to validate clue "{}" on map {} (bot pos : {})'.format(clue, destination, self.bot.position[0]))
                        if 'Phorreur' not in clue:
                            f.write('Clue was supposed to be at {}'.format(clue_pos))
                    hunt.error = True
                    hunt.reason = 'Could not validate clue'
                    break
                elif hunt.error:
                    break
                clues_left = self.bot.interface.get_clues_left()[0]

            step_valid = self.bot.interface.validate_hunt_step()[0]
            clues_left = self.bot.interface.get_clues_left()[0]
            if step_valid:
                clues_left = 0
            if (not hunt.error and not step_valid) or hunt.reason == 'phorror goto failed':
                last_clue = self.bot.interface.get_hunt_clue()
                if type(last_clue[0]) is str:
                    clue, direction = last_clue
                    last_valid_clue_pos = self.bot.interface.get_hunt_start()[0]
                    wrong_clue_pos = self.bot.llf.get_next_clue_pos(clue, last_valid_clue_pos, direction)
                    with open('../Utils/HuntErrorsLogBrief.txt', 'a') as f:
                        f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                        f.write('Failed to validate step because of clue "{}" going {} from {} (bot pos : {})'.format(clue, direction, last_valid_clue_pos, self.bot.position[0]))
                        f.write('Clue was supposed to be at {}'.format(wrong_clue_pos))

                    self.bot.llf.log(self.bot, '[Treasure Hunt {}] Error with a clue, trying to get to it...'.format(self.bot.id))
                    clues_left = self.bot.interface.get_clues_left()[0]
                    self.goto(last_valid_clue_pos)
                else:
                    with open('../Utils/HuntErrorsLogBrief.txt', 'a') as f:
                        f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                        f.write('Failed to validate step because of clue "{}". No nore details available as hunt ended)'.format(clue, self.bot.position[0]))

                found = False
                while not found and self.bot.interface.hunt_is_active()[0]:
                    direction_coords = [(0, -1), (0, 1), (-1, 0), (1, 0)][['n', 's', 'w', 'e'].index(direction)]
                    destination = [sum(x) for x in zip(self.bot.position[0], direction_coords)]
                    self.goto(destination, harvest=harvest)
                    if not (self.bot.position[0] in hunt.get_no_clue_list(clue)) and self.bot.position[0] != wrong_clue_pos:
                        self.bot.interface.validate_hunt_clue()
                        step_valid = self.bot.interface.validate_hunt_step()[0]
                        new_clues_left = self.bot.interface.get_clues_left()[0]
                        if step_valid or (new_clues_left != clues_left and new_clues_left):
                            found = True
                            hunt.error = False
                            hunt.added_clue = True
                            hunt.add_to_clue_list(clue, self.bot.position)
                            self.bot.llf.log(self.bot, '[Treasure Hunt {}] Discovered clue'.format(self.bot.id))
                        elif not step_valid and self.bot.interface.hunt_is_active()[0]:
                            hunt.add_to_no_clue_list(clue, self.bot.position)
                    elif self.bot.position[0] == wrong_clue_pos:
                        self.bot.llf.log(self.bot, '[Treasure Hunt {}] Removed clue'.format(self.bot.id))
                        hunt.add_to_no_clue_list(clue, self.bot.position)
                        hunt.remove_from_clue_list(clue, self.bot.position)

                if not found:
                    hunt.error = True
                    hunt.reason = 'Could not validate step'
                    hunt.current_step().validate(clues_left)
                    break
            elif hunt.error:
                hunt.current_step().validate(clues_left)
                break
            hunt.current_step().validate(clues_left)

        if hunt.error:
            self.bot.llf.log(self.bot, '[Treasure Hunt {}] Issue detected, abandoning hunt'.format(self.bot.id))
            with open('../Utils/HuntLogs.txt', 'w') as f:
                f.write(str(hunt))

            if not ('Non existing clue' in hunt.reason):
                in_hb = False
                result = self.bot.interface.abandon_hunt()[0]
                if type(result) is not bool:
                    if self.bot.interface.enter_heavenbag()[0]:
                        in_hb = True
                    time.sleep(result*60)
                if in_hb:
                    self.bot.interface.exit_heavenbag()
                    self.bot.interface.abandon_hunt()
        else:
            in_hb = False
            while self.bot.characteristics.health_percent < 100:
                if not in_hb:
                    if self.bot.interface.enter_heavenbag()[0]:
                        in_hb = True
                time.sleep(10)
                self.bot.interface.get_player_stats()
            if in_hb:
                self.bot.interface.exit_heavenbag()
            self.bot.interface.start_hunt_fight()

            chest_ids = [15248, 15260, 15261, 15262, 15264, 15265, 15266, 15267, 15268, 15269, 15270]
            inventory = self.bot.inventory.items
            for chest_id in chest_ids:
                number = self.bot.llf.get_number_of_item_in_inventory(inventory, chest_id)
                for i in range(number):
                    self.bot.interface.use_item(self.bot.llf.get_inventory_id(inventory, chest_id))

            if not self.bot.interface.hunt_is_active()[0]:
                self.bot.interface.new_hunt_timer = 0
                hunt.success = True
                hunt.reason = 'Stronk Af'
                with open('../Utils/HuntLogs.txt', 'w') as f:
                    f.write(str(hunt))
                self.bot.llf.log(self.bot, '[Treasure Hunt {}] Hunt successful'.format(self.bot.id))
            else:
                hunt.reason = 'Lost against chest'
        return hunt

    def hunt_treasures(self, duration_minutes, level='max', harvest=False):
        duration = duration_minutes * 60
        start = time.time()
        n_hunts = 0
        n_success = 0
        while time.time()-start < duration:
            try:
                n_hunts += 1
                hunt_start = time.time()
                self.bot.llf.log(self.bot, '[Treasure Hunt {}] Starting hunt #{}'.format(self.bot.id, n_hunts))
                hunt = self.tresure_hunt(level, harvest)
                self.bot.llf.hunts_to_db(self.bot.credentials['name'], round((time.time()-hunt_start)/60, 1), hunt.success, str(hunt), hunt.reason)
                n_success = n_success+1 if hunt.success else n_success
            except Exception:
                with open('../Utils/24botHoursTestRun.txt', 'a') as f:
                    f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                    f.write(traceback.format_exc())
        self.drop_to_bank(item_id_list='all', withdraw_items_to_sell=True)
        value_generated = self.sell_all()
        self.bot.llf.log(self.bot, '[Treasure Hunt {}] {} were started, {} were successful. ({}%)'.format(self.bot.id, n_hunts, n_success, round(n_success * 100 / n_hunts, 0)))
        self.bot.llf.log(self.bot, '[Treasure Hunt {}] Value generated : {}'.format(self.bot.id, value_generated))

    def fight_on_map(self, duration_minutes, hp_threshold=100):
        self.bot.occupation = 'Fighting'
        self.update_db()

        duration = duration_minutes*60
        start = time.time()
        while time.time()-start < duration:

            in_hb = False
            while self.bot.characteristics.health_percent < hp_threshold:
                if not in_hb and self.bot.characteristics.level >= 10:
                    if self.bot.interface.enter_heavenbag()[0]:
                        in_hb = True
                time.sleep(10)
            if in_hb:
                self.bot.interface.exit_heavenbag()

            mobs_on_map = self.bot.interface.get_monsters()
            mobs_on_map = False if not mobs_on_map else mobs_on_map[0]
            if mobs_on_map:
                mob_id, mob_ref, cell = mobs_on_map
                self.bot.interface.move(cell)
                self.bot.interface.attack_monster(mob_id)
            else:
                time.sleep(5)

    def update_hdv(self, close_after=True):
        self.bot.occupation = 'Updating HDV'
        self.update_db()
        hdv_content = self.bot.interface.open_hdv()
        items_for_sale = []
        if hdv_content and hdv_content[0] != 'empty':
            for batch in hdv_content:
                if batch not in items_for_sale:
                    items_for_sale.append(batch)

        for item in items_for_sale:
            item_hdv_stats = self.bot.interface.get_hdv_resource_stats(item[1])
            item_hdv_stats = False if not item_hdv_stats[0] else item_hdv_stats[0]
            if item_hdv_stats:
                batch_size_index = [1, 10, 100].index(item[2])
                if item[3] != item_hdv_stats[batch_size_index]:
                    new_price = item_hdv_stats[batch_size_index]-1
                    self.bot.interface.modify_price(item[1], item[2], new_price)
        if close_after:
            self.bot.interface.close_hdv()
        return hdv_content

    def sell_hdv(self, hdv_position=None):
        self.bot.occupation = 'Selling items'
        self.update_db()

        if self.bot.credentials['name'] in self.bot.resources.items_to_sell.keys():
            items_to_sell = self.bot.resources.items_to_sell[self.bot.credentials['name']]
        else:
            items_to_sell = self.bot.resources.items_to_sell['Default']

        current_map = list(self.bot.position[0]) if hdv_position is None else list(hdv_position)
        valid_map = False
        hdv_type = None

        for hdv, hdv_coords in self.bot.resources.hdv_pos.items():
            if current_map in hdv_coords and items_to_sell[hdv]:
                valid_map = True
                hdv_type = hdv
        # print('[SELL HDV] {}'.format(hdv_type))
        if valid_map:
            if hdv_position is None:
                selling = self.update_hdv()
            items = self.bot.inventory.items
            items_to_sell = items_to_sell[hdv_type]
            # print('[SELL HDV] Items : {},\n[SELL HDV] Items to sell {}'.format(items, items_to_sell))
            value = 0
            for item in items:
                # item looks like ['name', item_id, inv_id, number, inv_slot]

                if str(item[1]) in items_to_sell.keys() and item[3] >= items_to_sell[str(item[1])]["quantity"]:
                    # print('[SELL HDV] Item going to be sold : {}'.format(item))
                    hdv_list = []
                    for key in self.bot.resources.hdv_pos.keys():
                        hdv_list += self.bot.resources.hdv_pos[key]
                    if self.bot.position[1] == 1 and self.bot.position[0] in hdv_list:
                        self.bot.interface.open_hdv()
                        item_hdv_stats = self.bot.interface.get_hdv_resource_stats(item[1])
                        item_hdv_stats = False if item_hdv_stats[0] == [0, 0, 0] else item_hdv_stats[0]
                        # TODO What to do when item not for sale
                        if item_hdv_stats:
                            batch_size_index = [1, 10, 100].index(items_to_sell[str(item[1])]["quantity"])
                            batch_size = [1, 10, 100][batch_size_index]
                            price = item_hdv_stats[batch_size_index] - 1
                            player_lvl = self.bot.characteristics.level
                            if hdv_position is None and price > 0:
                                self.bot.llf.log(self.bot, '[Sell HDV {}] Selling {} batches of {} {} for {}'.format(self.bot.id, min(item[3] // batch_size, player_lvl-len(selling)), batch_size, item[0], price))
                                value += min(item[3] // batch_size, player_lvl-len(selling)) * price
                                self.bot.interface.sell_item(item[2], batch_size, min(item[3] // batch_size, player_lvl-len(selling)), price)
                    elif hdv_position is not None:
                        return True
            if hdv_position is None:
                self.bot.interface.close_hdv()
            return value
        else:
            return False

    def sell_all(self):
        if not self.bot.connected:
            self.bot.interface.connect()

        hdvs = []
        for position in self.bot.resources.hdv_pos.values():
            if self.bot.subscribed:
                hdvs.append(position[0])
            else:
                if len(position) == 3:
                    hdvs.append(position[2])

        hdv_route = []
        # Determines which hdvs the bot should visit to sell stuff
        for hdv in hdvs:
            if self.sell_hdv(hdv):
                hdv_route.append(hdv)

        total_value = 0
        for hdv in hdv_route:
            self.goto(hdv)
            value = self.sell_hdv()
            total_value = value + total_value if value else total_value
        return total_value

    def manage_dds(self):
        self.bot.occupation = 'Managing DDs'
        self.update_db()
        with open('../Utils/ddPath.json', 'r') as f:
            path = json.load(f)

        dd_to_pex_id = None
        for tile, cell in path:
            self.goto(tile, cell)
            tool = self.bot.llf.get_map_dd_tool(self.bot.position[0])
            dds_stable = []
            dds_paddock = []
            all_dds = self.bot.interface.open_dd()
            if all_dds:
                for dd in all_dds:
                    dd_obj = DD(dd)
                    if dd_obj.in_paddock:
                        dds_paddock.append(dd_obj)
                    else:
                        dds_stable.append(dd_obj)

            self.bot.llf.log(self.bot, '[DD Manager {}] Current tool : {}'.format(self.bot.id, tool))
            self.bot.llf.log(self.bot, '[DD Manager {}] DDs in stable : {}'.format(self.bot.id, len(dds_stable)))
            self.bot.llf.log(self.bot, '[DD Manager {}] DDs in paddock : {}'.format(self.bot.id, len(dds_paddock)))

            # Pull sterile non pregnant dds
            for dd in dds_stable:
                if dd.get_next_spot() == 'out':
                    self.bot.interface.put_dd_in_inventory(dd.id, "stable")
                if dd.get_next_spot() == 'pex':
                    dd_to_pex_id = dd.id

            # Retrieve DDs from paddock
            # Make sure there is at least 5 spots free first (dd + the 4 eventual children)
            # Put the dds with the lowest scores in the inventory until there is enough room
            for dd in dds_paddock:
                n_dds_to_kick = len(dds_stable)-245 if len(dds_stable)-245 > 0 else 0
                if n_dds_to_kick:
                    self.bot.llf.score_dds(dds_stable)
                    score_sorted_dds = sorted(dds_stable, key=lambda one_dd: one_dd.score)
                    dds_to_kick = score_sorted_dds[:n_dds_to_kick]
                    for dd_to_kick in dds_to_kick:
                        self.bot.llf.log(self.bot, '[DD Manager{} ] Kicking dd {}. Score : {}'.format(self.bot.id, dd_to_kick.id, dd_to_kick.score))
                        self.bot.interface.put_dd_in_inventory(dd_to_kick.id, "stable")
                        del dds_stable[dds_stable.index(dd_to_kick)]

                dds_stable.append(dd)
                self.bot.interface.put_dd_in_stable(dd.id, "paddock")

            if tool == 'mood+':
                males_for_mating = []
                females_for_mating = []
                for dd in dds_stable:
                    if dd.is_fecondation_ready and dd.sex == 'male' and len(males_for_mating) < 5:
                        males_for_mating.append(dd)
                    elif dd.is_fecondation_ready and dd.sex == 'female' and len(females_for_mating) < 5:
                        females_for_mating.append(dd)
                males_for_mating = males_for_mating[:min(len(males_for_mating), len(females_for_mating))]
                females_for_mating = females_for_mating[:min(len(males_for_mating), len(females_for_mating))]
                dds_for_mating = males_for_mating + females_for_mating
                for dd in dds_for_mating:
                    self.bot.interface.put_dd_in_paddock(dd.id, 'stable')
                if len(dds_for_mating):
                    time.sleep(3)
                    self.bot.interface.fart()
                    time.sleep(3)
                for dd in dds_for_mating:
                    self.bot.interface.put_dd_in_stable(dd.id, 'paddock')

            dds_for_tool = []
            exhausted_dd_for_tool = []
            for dd in dds_stable:
                if dd.get_next_spot() == tool and dd.fatigue < 100:
                    dds_for_tool.append(dd)
                elif dd.get_next_spot() == tool and dd.fatigue == 100:
                    exhausted_dd_for_tool.append(dd)

            fatigue_sorted_dds_for_tool = sorted(dds_for_tool, key=lambda one_dd: one_dd.fatigue)
            fatigue_sorted_dds_for_tool += exhausted_dd_for_tool
            if len(fatigue_sorted_dds_for_tool) >= 10:
                selected_dds = fatigue_sorted_dds_for_tool[:10]
            else:
                selected_dds = fatigue_sorted_dds_for_tool

            for dd in selected_dds:
                self.bot.interface.put_dd_in_paddock(dd.id, "stable")

            bot_mobile = False
            if dd_to_pex_id is not None and tuple(self.bot.position[0]) == (-32, 38):
                bot_mobile = self.pex_dd(dd_to_pex_id)
            self.bot.interface.close_dd()
            if bot_mobile:
                self.bot.interface.mount_dd()

    def manage_dds_duration(self, duration_minutes):
        duration = duration_minutes * 60
        start = time.time()
        while time.time()-start < duration:
            self.manage_dds()

    def pex_dd(self, dd_id):
        if self.bot.characteristics.level >= 60:
            dd_stats = self.bot.interface.get_dd_stat()
            if dd_stats[0]:
                self.bot.interface.put_dd_in_stable(dd_stats[2], 'equip')
            self.bot.interface.equip_dd(dd_id, 'stable')
            self.bot.interface.close_dd()
        self.bot.interface.mount_dd()
        self.bot.interface.set_dd_xp(90)
        dd_stats = self.bot.interface.get_dd_stat()
        while dd_stats[0] <= 5:
            self.hunt_treasures(1)
            dd_stats = self.bot.interface.get_dd_stat()
        self.bot.interface.set_dd_xp(0)
        self.goto((-37, -56), target_cell=219)
        self.bot.interface.dismount_dd()
        dds = self.bot.interface.open_dd()
        self.bot.interface.put_dd_in_stable(dd_id, 'equip')
        bot_mobile_id = self.bot.llf.get_bot_mobile(dds)
        if bot_mobile_id:
            self.bot.interface.equip_dd(bot_mobile_id, 'stable')
            return True

    def use_schedule(self, schedule_name=None):
        if schedule_name is not None:
            with open('../Utils/Schedules/{}.json'.format(schedule_name), 'r') as f:
                schedules = json.load(f)
            schedule = []
            for schedule_curr in schedules:
                if schedule_curr['idx'] == 0:
                    schedule = schedule_curr['tasks']
        else:
            schedule = self.bot.schedule

        caught_up = False
        task_number = 0

        while 1:
            current_task = schedule[task_number % len(schedule)]
            previous_task = schedule[(task_number - 1) % len(schedule)]
            if not caught_up and (previous_task['end'] * 3600 < time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec < current_task['end'] * 3600 or time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec < schedule[0]['end'] * 3600 or time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec > schedule[-1]['end'] * 3600):
                caught_up = True

            if caught_up:
                if not (current_task['start'] * 3600 < time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec < current_task['end'] * 3600):
                    secs_left = (current_task['start'] * 3600 + (86400 - (time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec))) % 86400
                    self.bot.occupation = 'Sleeping'
                    self.update_db()
                    self.bot.interface.disconnect()
                    self.bot.llf.log(self.bot, '[Scheduler {}] Sleeping for {} minutes, waking up at {}'.format(self.bot.id, secs_left // 60, str(datetime.datetime.fromtimestamp(time.time() + secs_left).time())[:-7]))
                    time.sleep(secs_left)

                if current_task['end'] * 3600 < time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec:
                    secs_left = current_task['end'] * 3600 + (86400 - (time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec))
                else:
                    secs_left = current_task['end'] * 3600 - (time.localtime().tm_hour * 3600 + time.localtime().tm_min * 60 + time.localtime().tm_sec)

                minutes_left = secs_left // 60
                if current_task['name'] == 'dd':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.bot.llf.log(self.bot, '[Scheduler {}] Starting to manage DDs'.format(self.bot.id))
                    self.manage_dds_duration(minutes_left)
                elif current_task['name'] == 'hunt':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.bot.llf.log(self.bot, '[Scheduler {}] Starting to hunt for {} minutes'.format(
                        self.bot.id, round(minutes_left, 0)))
                    self.hunt_treasures(minutes_left)
                elif current_task['name'] == 'huntGather':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.bot.llf.log(self.bot, '[Scheduler {}] Starting to hunt and gather for {} minutes'.format(
                        self.bot.id, round(minutes_left, 0)))
                    self.hunt_treasures(minutes_left, harvest=True)
                elif current_task['name'] == 'sell':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.bot.llf.log(self.bot, '[Scheduler {}] Starting to sell items'.format(
                        self.bot.id))
                    self.drop_to_bank('all', True)
                    self.sell_all()
                elif current_task['name'] == 'sleep':
                    self.bot.llf.log(self.bot, '[Scheduler {}] Sleeping for {} minutes'.format(
                        self.bot.id, round(minutes_left)))
                    self.bot.interface.disconnect()
                    time.sleep(60 * minutes_left)
                elif current_task['name'] == 'harvest':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.bot.llf.log(self.bot, '[Scheduler {}] Starting to harvest resources'.format(self.bot.id))
                    self.harvest_path(minutes_left, sell=True)

            task_number += 1

    def drop_bot_mobile(self, idx):
        self.goto((-32, 37), 271)
        self.bot.interface.dismount_dd()
        dds_stable = []
        dds_paddock = []
        all_dds = self.bot.interface.open_dd()
        for dd in all_dds:
            dd_obj = DD(dd)
            if dd_obj.in_paddock:
                dds_paddock.append(dd_obj)
            else:
                dds_stable.append(dd_obj)
        if len(dds_paddock) == 10:
            self.bot.interface.put_dd_in_stable(dds_paddock[0].id, 'paddock')
        self.bot.interface.put_dd_in_paddock(idx, 'equip')
        self.bot.interface.close_dd()
        self.bot.mount = 'resting'
        self.bot.llf.set_mount_situation(self.bot.credentials['name'], 'resting')
        self.bot.occupation = 'Putting Bot-Mobile in paddock'
        self.update_db()

    def fetch_bot_mobile(self):
        self.bot.occupation = 'Fetching Bot-Mobile'
        self.update_db()
        self.goto((-32, 37), 271)
        all_dds = self.bot.interface.open_dd()
        bm_id = self.bot.llf.get_bot_mobile(all_dds)
        if bm_id:
            self.bot.interface.equip_dd(bm_id, 'paddock')
        self.bot.interface.close_dd()
        self.bot.interface.mount_dd()
        self.bot.mount = 'equipped'
        self.bot.llf.set_mount_situation(self.bot.credentials['name'], 'equipped')

    def update_db(self):
        kamas = -1 if self.bot.inventory.kamas is None else self.bot.inventory.kamas
        level = -1 if self.bot.characteristics.level is None else self.bot.characteristics.level
        occupation = "Unknown" if self.bot.occupation is None else self.bot.occupation
        position = ((0, 0), 0) if self.bot.position is None else self.bot.position
        try:
            self.bot.llf.log(self.bot, '[Database {}] Uploading {}, {}, {}, {}, {}, {}, {}, {}'.format(self.bot.id, self.bot.id, self.bot.credentials['server'], self.bot.credentials['name'], kamas, level, occupation, position[0], position[1]))
            self.bot.llf.update_db(
                self.bot.id,
                self.bot.credentials['server'],
                self.bot.credentials['name'],
                kamas,
                level,
                occupation,
                position[0],
                position[1]
            )
        except TypeError:
            # Degraded upload
            self.bot.llf.log(self.bot, '[Database {}] Uploading {}, {}, {}'.format(self.bot.id, self.bot.id, self.bot.credentials['server'], self.bot.credentials['name']))
            self.bot.llf.update_db(
                self.bot.id,
                self.bot.credentials['server'],
                self.bot.credentials['name'],
                kamas,
                level,
                occupation
            )
        except Exception:
            with open('../Utils/DatabaseErrorLog.txt', 'a') as f:
                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                f.write(traceback.format_exc())

    def get_hdv_prices(self, hdv, batch_id):
        hdv_pos = self.bot.llf.closest_coord(self.bot.position[0], self.bot.resources.hdv_pos[hdv])
        start = time.time()
        items_ids = self.bot.resources.hdv2id[hdv]
        self.goto(hdv_pos)
        self.bot.interface.open_hdv()
        self.bot.llf.log(self.bot, '[HDV Scraper {}] Starting scraping'.format(self.bot.id))
        if hdv == 'Equipements':
            self.bot.interface.get_hdv_item_stats(items_ids, batch_id)
        if hdv in ['Ressources', 'Runes', 'Consommables']:
            self.bot.interface.get_hdv_resource_stats(items_ids, batch_id)
            # self.bot.llf.resource_item_to_db(self.bot, stats, item_type='Resource')

        self.bot.llf.log(self.bot, '[HDV Scraper {}] Done in {}m, {}s'.format(self.bot.id, round((time.time() - start) // 60, 0), round((time.time() - start) % 60, 0)))
        self.bot.interface.close_hdv()

    def scrape_hdvs(self):
        batch_id = self.bot.llf.last_batch_id() + 1
        self.get_hdv_prices('Equipements', batch_id)
        self.get_hdv_prices('Ressources', batch_id)
        self.get_hdv_prices('Runes', batch_id)
        self.get_hdv_prices('Consommables', batch_id)

    def buy_ingredients(self, item_id_list):
        item_id_list = [item_id_list] if type(item_id_list) is int else item_id_list
        recipes = []
        ingredients = {}
        for item_id in item_id_list:
            for recipe in self.bot.resources.recipes:
                if item_id == recipe['resultId']:
                    recipes.append(recipe)
                    for ingredient, quantity in recipe['Ingredients']:
                        if ingredient in ingredients.keys():
                            ingredients[ingredient] += quantity
                        else:
                            ingredients[ingredient] = quantity

        grocery_list = pd.DataFrame()
        grocery_list['Ingredients'] = pd.Series(ingredients)

        grocery_list['Hdv'] = [self.bot.resources.id2hdv[str(ingredient_id)] for ingredient_id in list(grocery_list.index)]
        ingredient_items = grocery_list[grocery_list.Hdv == 'Equipements'].index.tolist()
        items = self.bot.ds.database.items_from_id(self.bot.credentials['server'], ingredient_items).drop(columns=['Time', 'Name', 'Stats', 'Hash'])
        grocery_list['Price'] = items.groupby('ItemId')['Price'].min().append(self.bot.ds.database.resources_from_id(self.bot.credentials['server'], grocery_list['Ingredients'].index.tolist()).Price1)

        grocery_list['Batch1'] = grocery_list.Ingredients.apply(lambda ingredient: ingredient if ingredient < 5 else 0)
        grocery_list['Batch10'] = grocery_list.Ingredients.apply(lambda ingredient: ceil(ingredient / 10) - 10 * int(ingredient / 100) if ingredient >= 5 else 0)
        grocery_list['Batch100'] = grocery_list.Ingredients.apply(lambda ingredient: ingredient // 100)

        stores = set(grocery_list['Hdv'].tolist())
        items_bought = {}
        for store in stores:
            self.goto(self.bot.llf.closest_coord(self.bot.position[0], self.bot.resources.hdv_pos[store]))
            self.bot.interface.open_hdv()
            for item in grocery_list[grocery_list.Hdv == store].iterrows():
                item_id = item[0]
                for batch in ['Batch1', 'Batch10', 'Batch100']:
                    if item[1][batch]:
                        number_bought, money_spent = self.bot.interface.buy_resource(item_id, int(batch.replace('Batch', '')), item[1][batch], int(item[1].Price * 1.5))
                        if item_id in items_bought.keys():
                            items_bought[item_id] = [items_bought[item_id][0] + number_bought, items_bought[item_id][1] + money_spent]
                        else:
                            items_bought[item_id] = [number_bought, money_spent]
            self.bot.interface.close_hdv()
        items_bought = pd.DataFrame(items_bought)

        # TODO determining which items are actually craftable, and for which price
        # TODO Eventually sell remaining resources
        # print(grocery_list)

__author__ = 'Alexis'
