from Model.Pathfinder import PathFinder
from Model.LowLevelFunctions import LowLevelFunctions
from Model.DD import DD
import json
import time
import datetime
import traceback


class HighLevelFunctions:
    def __init__(self, bot):
        self.bot = bot
        self.llf = LowLevelFunctions()
        self.brak_maps = self.llf.get_brak_maps()
        self.bwork_maps = self.llf.get_bwork_maps()

    def goto(self, target_coord, target_cell=None, worldmap=1):
        current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

        with open('..//Utils//gotos.txt', 'a') as f:
            f.write('\n\n' + str(datetime.datetime.now()) + '\n')
            f.write('Going from map {}, cell {} to map {}, worldmap : {}'.format(current_map, current_cell, target_coord, worldmap))

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
                teleport_cell = self.llf.get_closest_walkable_cell(statue_cell, statue_map, current_worldmap)
                self.bot.interface.move(teleport_cell)
                self.bot.interface.go_to_incarnam()
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()

            elif list(current_map) == [-25, -36] and current_worldmap == -1:
                # bot is in hunting hall
                self.bot.interface.exit_hunting_hall()

            elif current_worldmap == -1:
                closest_zaap = self.llf.get_closest_known_zaap(self.bot.credentials['name'], target_coord)
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
                        if self.bot.interface.get_player_stats()[0]['Lvl'] < 10:
                            raise RuntimeError('Bot is not level 10 an can not enter heavenbag')
                        else:
                            raise RuntimeError('Unable to enter heavenbag to go from {}, worldmap -1 to {}'.format(current_map, closest_zaap))

            # TODO manage more worldmap changing
            else:
                raise RuntimeError('Worldmap change not supported')

        closest_zaap = self.llf.get_closest_known_zaap(self.bot.credentials['name'], target_coord)
        if closest_zaap is not None:
            distance_zaap_target = self.llf.distance_coords(closest_zaap, tuple(target_coord))
            if worldmap == current_worldmap and self.llf.distance_coords(current_map, tuple(target_coord)) > distance_zaap_target+5:
                if self.bot.interface.enter_heavenbag()[0]:
                    self.bot.interface.use_zaap(closest_zaap)
                    while tuple(current_map) != tuple(closest_zaap):
                        current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                        time.sleep(2)

        if list(current_map) not in self.brak_maps and list(target_coord) in self.brak_maps:
            # Bot needs to enter brak
            if self.bot.interface.enter_heavenbag()[0]:
                self.bot.interface.use_zaap((-26, 35))
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
        if list(current_map) in self.brak_maps and list(target_coord) not in self.brak_maps:
            # Bot needs to exit brak
            # TODO
            pass
        if list(current_map) not in self.bwork_maps and list(target_coord) in self.bwork_maps:
            # Bot needs to enter bwork village
            self.goto((-1, 8), target_cell=383)
            self.bot.interface.enter_bwork()
            current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
        if list(current_map) in self.bwork_maps and list(target_coord) not in self.bwork_maps:
            # Bot needs to exit bwork village
            self.goto((-2, 8), target_cell=260)
            self.bot.interface.exit_bwork()
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
                current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
                self.bot.position = current_map, current_worldmap
                if current_worldmap == 1:
                    self.llf.add_discovered_zaap(self.bot.credentials['name'], self.bot.position)
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
        closest_unk_zaap = self.llf.get_closest_unknown_zaap(self.bot.credentials['name'], self.bot.position[0])
        while closest_unk_zaap:
            self.goto(closest_unk_zaap)
            self.llf.add_discovered_zaap(self.bot.credentials['name'], self.bot.position[0])
            closest_unk_zaap = self.llf.get_closest_unknown_zaap(self.bot.credentials['name'], self.bot.position[0])

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
            # print('[Harvest] harvestable : {}'.format(harvestable))

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
            # print('[Harvest] harvest spot : {}'.format(harvest_spots))

            if harvest_spots:
                success = True
                selected_cell = self.llf.closest_cell(player_pos, [spot[0] for spot in harvest_spots])
                if not self.bot.interface.move(selected_cell)[0]:
                    success = False
                resource_cell = self.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots])
                resource_name = harvestable_match_res_name[harvestable.index(resource_cell)]
                ret_val = self.bot.interface.harvest_resource(resource_cell)
                if len(ret_val) == 1:
                    success = False
                else:
                    ret_val = ret_val[0], resource_name, ret_val[1], ret_val[2], ret_val[3]

                if not success:
                    inacessible_res = self.llf.closest_cell(selected_cell, [spot[1] for spot in harvest_spots])
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

        with open('..//Utils//HarvestLog_{}.txt'.format(self.bot.id), 'a') as f:
            for item in harvest:
                f.write('ID : {}, Item : {}, Number : {}, Weight : {}\n'.format(item[0], item[1], item[2], int(item[3]*100/item[4])))
        if type(ret_val) is tuple and ret_val[3]+5 >= ret_val[4]:
            self.llf.log(self.bot, '[Harvest {}] Full'.format(self.bot.id))
            return False
        else:
            self.llf.log(self.bot, '[Harvest {}] Done'.format(self.bot.id))
            return True

    def harvest_path(self, path, number_of_loops=-1, harvest_only=None, do_not_harvest=None, sell=False):
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
                    self.drop_to_bank('all', withdraw_items_to_sell=sell)
                    if sell:
                        self.sell_all(self.bot.subscribed)
                    self.goto(tile, target_cell=target_cell, worldmap=worldmap)
                    full = not self.harvest_map(harvest_only, do_not_harvest)

    def withdraw_items_to_sell_from_bank(self, player_stats, bank_contents):
        self.bot.occupation = 'Withdrawing items from bank'
        self.update_db()
        with open('..//Utils//ItemsToSell.json', 'r') as f:
            items_to_sell = json.load(f)
        if self.bot.credentials['name'] in items_to_sell.keys():
            items_to_sell = items_to_sell[self.bot.credentials['name']]
        else:
            items_to_sell = items_to_sell['Default']

        item_to_sell_ids = [int(key) for hdv_name in items_to_sell.keys() for key in items_to_sell[hdv_name]]
        item_to_sell_batch_size = [items_to_sell[hdv_name][key]['quantity'] for hdv_name in items_to_sell.keys() for key in items_to_sell[hdv_name]]
        for item_id in item_to_sell_ids:
            unique_id = self.llf.get_inventory_id(bank_contents['Items'], item_id)
            number = self.llf.get_number_of_item_in_inventory(bank_contents['Items'], item_id)
            weight = self.llf.get_weight_of_item_in_inventory(bank_contents['Items'], item_id)
            inv_space = player_stats['WeightMax'] - player_stats['Weight']
            quantity_to_withdraw = min(number, int(inv_space / weight)) if weight else number
            batch_size = item_to_sell_batch_size[item_to_sell_ids.index(item_id)]
            round_quantity_to_withdraw = quantity_to_withdraw//batch_size*batch_size
            if round_quantity_to_withdraw:
                player_stats, bank_contents = self.bot.interface.get_from_bank_unique(unique_id, round_quantity_to_withdraw)

    def drop_to_bank(self, item_id_list='all', withdraw_items_to_sell=False):
        self.bot.occupation = 'Dropping to bank'
        self.update_db()
        if not tuple(self.bot.position[0]) == (4, -16):
            self.goto((4, -16))
        bank_entrance, bank_exit = self.bot.interface.get_bank_door_cell()
        if bank_entrance:
            self.bot.interface.move(bank_entrance)
            self.bot.interface.enter_bank()
            self.bot.interface.open_bank()
            player_stats, bank_contents = self.bot.interface.drop_in_bank_list(item_id_list)
            if withdraw_items_to_sell:
                self.withdraw_items_to_sell_from_bank(player_stats, bank_contents)
            self.bot.interface.get_kamas_from_bank('all')
            self.bot.interface.close_bank()
            self.bot.interface.move(bank_exit)
            return player_stats, bank_contents
        else:
            raise Exception('Not a map with a bank')

    def tresure_hunt(self, level='max'):
        def get_hunt(level):
            self.bot.occupation = 'Getting a new hunt'
            self.update_db()
            self.goto((-25, -36), worldmap=1)
            door = self.bot.interface.get_hunting_hall_door_cell()[0]
            self.bot.interface.move(self.llf.get_closest_walkable_cell(door, self.bot.position[0], self.bot.position[1]))
            self.bot.interface.enter_hunting_hall()
            while not self.bot.interface.get_new_hunt(level)[0]:
                self.llf.log(self.bot, '[Treasure Hunt {}] Getting new hunt'.format(self.bot.id))
                time.sleep(30)
            self.bot.interface.exit_hunting_hall()

        if level == 'max':
            level = self.bot.interface.get_player_stats()[0]['Lvl']
        level = int(level/20)*20

        if not self.bot.interface.hunt_is_active()[0]:
            get_hunt(level)
        hunt_start = self.bot.interface.get_hunt_start()[0]
        self.goto(hunt_start)

        self.bot.occupation = 'Treasure Hunting'
        self.update_db()

        hunt_error_flag = False
        reason = ''
        while self.bot.interface.get_steps_left()[0] and not hunt_error_flag:
            clue, direction, start_pos, clue_pos = None, None, None, None
            while self.bot.interface.get_clues_left()[0] and not hunt_error_flag:
                clue, direction = self.bot.interface.get_hunt_clue()
                start_pos = self.bot.position[0]
                destination = None
                if 'Phorreur' in clue:
                    n_steps = 0
                    while not (self.bot.interface.check_for_phorror()[0] == clue) and n_steps <= 11:
                        direction_coords = [(0, -1), (0, 1), (-1, 0), (1, 0)][['n', 's', 'w', 'e'].index(direction)]
                        try:
                            destination = [sum(x) for x in zip(self.bot.position[0], direction_coords)]
                            self.goto(destination)
                        except Exception as e:
                            with open('..//Utils//HuntErrorsLog.txt', 'a') as f:
                                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                                f.write(traceback.format_exc())
                            with open('..//Utils//HuntErrorsLogBrief.txt', 'a') as f:
                                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                                f.write('Could not go to {} from {} to find {}'.format(destination, self.bot.position, clue))
                            hunt_error_flag = True
                            reason = 'Goto fail'
                            break
                else:
                    try:
                        clue_pos = self.llf.get_next_clue_pos(clue, self.bot.position[0], direction)
                        self.goto(clue_pos)
                    except Exception as e:
                        with open('..//Utils//HuntErrorsLog.txt', 'a') as f:
                            f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                            f.write(traceback.format_exc())

                        with open('..//Utils//HuntErrorsLogBrief.txt', 'a') as f:
                            f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                            f.write(e.args[0])

                        hunt_error_flag = True
                        reason = 'Could not find clue'
                        break

                if not self.bot.interface.validate_hunt_clue()[0] and not hunt_error_flag:
                    with open('..//Utils//HuntErrorsLogBrief.txt', 'a') as f:
                        f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                        f.write('Failed to validate clue "{}" on map {} (bot pos : {})'.format(clue, destination, self.bot.position[0]))
                        f.write('Clue was supposed to be at {}'.format(clue_pos))
                    hunt_error_flag = True
                    reason = 'Could not validate clue'
                    break
                elif hunt_error_flag:
                    break

            if not self.bot.interface.validate_hunt_step()[0] and not hunt_error_flag:
                clue, direction = self.bot.interface.get_hunt_clue()
                with open('..//Utils//HuntErrorsLogBrief.txt', 'a') as f:
                    f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                    f.write('Failed to validate step because of clue "{}" going {} from {} (bot pos : {})'.format(clue, direction, start_pos, self.bot.position[0]))
                    f.write('Clue was supposed to be at {}'.format(clue_pos))
                hunt_error_flag = True
                reason = 'Could not validate step'
                break
            elif hunt_error_flag:
                break

        if hunt_error_flag:
            self.llf.log(self.bot, '[Treasure Hunt {}] Issue detected, abandoning hunt'.format(self.bot.id))
            in_hb = False
            while not self.bot.interface.abandon_hunt()[0]:
                if not in_hb:
                    if self.bot.interface.enter_heavenbag()[0]:
                        in_hb = True
                time.sleep(30)
            if in_hb:
                self.bot.interface.exit_heavenbag()
            return False, reason
        else:
            in_hb = False
            while self.bot.interface.get_player_stats()[0]['Health'] < 100:
                if not in_hb:
                    if self.bot.interface.enter_heavenbag()[0]:
                        in_hb = True
                time.sleep(10)
            if in_hb:
                self.bot.interface.exit_heavenbag()
            self.bot.interface.start_hunt_fight()

            chest_ids = [15248, 15260, 15261, 15262, 15264, 15265, 15266, 15267, 15268, 15269, 15270]
            inventory = self.bot.interface.get_player_stats()[0]['Inventory']['Items']
            for chest_id in chest_ids:
                number = self.llf.get_number_of_item_in_inventory(inventory, chest_id)
                for i in range(number):
                    self.bot.interface.use_item(self.llf.get_inventory_id(inventory, chest_id))

            if not self.bot.interface.hunt_is_active()[0]:
                self.llf.log(self.bot, '[Treasure Hunt {}] Hunt successful'.format(self.bot.id))
                return True, 'Stronk Af'
            else:
                return False, 'Lost against chest'

    def hunt_treasures(self, duration_minutes, level='max'):
        duration = duration_minutes * 60
        start = time.time()
        n_hunts = 0
        n_success = 0
        while time.time()-start < duration:
            try:
                n_hunts += 1
                hunt_start = time.time()
                self.llf.log(self.bot, '[Treasure Hunt {}] Starting hunt #{}'.format(self.bot.id, n_hunts))
                success, reason = self.tresure_hunt(level)
                self.llf.hunts_to_db(self.bot.credentials['name'], round((time.time()-hunt_start)/60, 1), success, reason)
                n_success = n_success+1 if success else n_success
            except Exception:
                with open('..//Utils//24botHoursTestRun.txt', 'a') as f:
                    f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                    f.write(traceback.format_exc())
        self.llf.log(self.bot, '[Treasure Hunt {}] {} were started, {} were successful. ({}%)'.format(self.bot.id, n_hunts, n_success, round(n_success*100/n_hunts, 0)))

    def fight_on_map(self, duration_minutes, hp_threshold=100):
        self.bot.occupation = 'Fighting'
        self.update_db()

        duration = duration_minutes*60
        start = time.time()
        while time.time()-start < duration:

            in_hb = False
            while self.bot.interface.get_player_stats()[0]['Health'] < hp_threshold:
                if not in_hb and self.bot.interface.get_player_stats()[0]['Lvl'] >= 10:
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
            item_hdv_stats = self.bot.interface.get_hdv_item_stats(item[1])
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
        with open('..//Utils//hdv_pos.json', 'r') as f:
            hdv_pos = json.load(f)
        with open('..//Utils//ItemsToSell.json', 'r') as f:
            items_to_sell = json.load(f)

        if self.bot.credentials['name'] in items_to_sell.keys():
            items_to_sell = items_to_sell[self.bot.credentials['name']]
        else:
            items_to_sell = items_to_sell['Default']

        current_map = list(self.bot.position[0]) if hdv_position is None else list(hdv_position)
        valid_map = False
        hdv_type = None

        for hdv, hdv_coords in hdv_pos.items():
            if current_map in hdv_coords and items_to_sell[hdv]:
                valid_map = True
                hdv_type = hdv
        # print('[SELL HDV] {}'.format(hdv_type))
        if valid_map:
            if hdv_position is None:
                selling = self.update_hdv()
            items = self.bot.interface.get_player_stats()[0]['Inventory']['Items']
            items_to_sell = items_to_sell[hdv_type]
            # print('[SELL HDV] Items : {},\n[SELL HDV] Items to sell {}'.format(items, items_to_sell))
            for item in items:
                # item looks like ['name', item_id, inv_id, number, inv_slot]

                if str(item[1]) in items_to_sell.keys() and item[3] >= items_to_sell[str(item[1])]["quantity"]:
                    # print('[SELL HDV] Item going to be sold : {}'.format(item))
                    hdv_list = []
                    for key in hdv_pos.keys():
                        hdv_list += hdv_pos[key]
                    if self.bot.position[1] == 1 and self.bot.position[0] in hdv_list:
                        self.bot.interface.open_hdv()
                        item_hdv_stats = self.bot.interface.get_hdv_item_stats(item[1])
                        item_hdv_stats = False if item_hdv_stats[0] == [0, 0, 0] else item_hdv_stats[0]
                        # TODO What to do when item not for sale
                        if item_hdv_stats:
                            batch_size_index = [1, 10, 100].index(items_to_sell[str(item[1])]["quantity"])
                            batch_size = [1, 10, 100][batch_size_index]
                            price = item_hdv_stats[batch_size_index] - 1
                            player_lvl = self.bot.interface.get_player_stats()[0]['Lvl']
                            if hdv_position is None and price > 0:
                                self.llf.log(self.bot, '[Sell HDV {}] Selling {} batches of {} {} for {}'.format(self.bot.id, min(item[3] // batch_size, player_lvl-len(selling)), batch_size, item[0], price))
                                self.bot.interface.sell_item(item[2], batch_size, min(item[3] // batch_size, player_lvl-len(selling)), price)
                    elif hdv_position is not None:
                        return True
            if hdv_position is None:
                self.bot.interface.close_hdv()
        else:
            return False

    def sell_all(self, subscribed):
        if not self.bot.connected:
            self.bot.interface.connect()
        with open('..//Utils//hdv_pos.json', 'r') as f:
            all_hdvs = json.load(f)

        hdvs = []
        for position in all_hdvs.values():
            if subscribed:
                hdvs.append(position[0])
            else:
                if len(position) == 3:
                    hdvs.append(position[2])

        hdv_route = []
        # Determines which hdvs the bot should visit to sell stuff
        for hdv in hdvs:
            if self.sell_hdv(hdv):
                hdv_route.append(hdv)

        for hdv in hdv_route:
            self.goto(hdv)
            self.sell_hdv()

    def manage_dds(self):
        self.bot.occupation = 'Managing DDs'
        self.update_db()
        with open('..//Utils//ddPath.json', 'r') as f:
            path = json.load(f)

        dd_to_pex_id = None
        for tile, cell in path:
            self.goto(tile, cell)
            tool = self.llf.get_map_dd_tool(self.bot.position[0])
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

            self.llf.log(self.bot, '[DD Manager {}] Current tool : {}'.format(self.bot.id, tool))
            self.llf.log(self.bot, '[DD Manager {}] DDs in stable : {}'.format(self.bot.id, len(dds_stable)))
            self.llf.log(self.bot, '[DD Manager {}] DDs in paddock : {}'.format(self.bot.id, len(dds_paddock)))

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
                    self.llf.score_dds(dds_stable)
                    score_sorted_dds = sorted(dds_stable, key=lambda one_dd: one_dd.score)
                    dds_to_kick = score_sorted_dds[:n_dds_to_kick]
                    for dd_to_kick in dds_to_kick:
                        self.llf.log(self.bot, '[DD Manager{} ] Kicking dd {}. Score : {}'.format(self.bot.id, dd_to_kick.id, dd_to_kick.score))
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
        if self.bot.interface.get_player_stats()[0]['Lvl'] >= 80:
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
        bot_mobile_id = self.llf.get_bot_mobile(dds)
        if bot_mobile_id:
            self.bot.interface.equip_dd(bot_mobile_id, 'stable')
            return True

    def use_schedule(self, schedule_name=None):
        if schedule_name is not None:
            with open('..//Utils//Schedules//{}.json'.format(schedule_name), 'r') as f:
                schedules = json.load(f)
            schedule = []
            for schedule_curr in schedules:
                if schedule_curr['idx'] == 0:
                    schedule = schedule_curr['tasks']
        else:
            schedule = self.bot.schedule

        while 1:
            if schedule_name is None:
                self.bot.schedule = self.llf.get_schedule(self.bot.credentials['name'])
                schedule = self.bot.schedule

            caught_up = False
            for task in schedule:
                if (time.localtime().tm_hour + time.localtime().tm_min/60) - task['end'] < 0 or caught_up:
                    caught_up = True
                    pass
                elif task == schedule[-1] and not caught_up:
                    self.llf.log(self.bot, '[Scheduler {}] Sleeping for {} minutes'.format(
                        self.bot.id, round(60 * (24 - (time.localtime().tm_hour + time.localtime().tm_min / 60)) + 2)) + self.bot.interface.end_color)
                    self.bot.interface.disconnect()
                    time.sleep(3600 * (24 - (time.localtime().tm_hour + time.localtime().tm_min / 60)) + 2)
                else:
                    continue
                if task['start'] > (time.localtime().tm_hour + time.localtime().tm_min / 60):
                    self.bot.interface.disconnect()
                    minutes_left = (task['start'] - (time.localtime().tm_hour + time.localtime().tm_min / 60))*60
                    self.bot.occupation = 'Sleeping'
                    self.update_db()
                    self.llf.log(self.bot, '[Scheduler {}] Sleeping for {} minutes'.format(
                        self.bot.id, round(minutes_left)) + self.bot.interface.end_color)
                    time.sleep(60 * minutes_left)

                minutes_left = max(1, 60 * (task['end'] - (time.localtime().tm_hour + time.localtime().tm_min / 60)))
                if task['name'] == 'dd':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.llf.log(self.bot, '[Scheduler {}] Starting to manage DDs'.format(self.bot.id) + self.bot.interface.end_color)
                    self.manage_dds_duration(minutes_left)
                elif task['name'] == 'hunt':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.llf.log(self.bot, '[Scheduler {}] Starting to hunt for {} minutes'.format(
                        self.bot.id, round(minutes_left, 0)) + self.bot.interface.end_color)
                    self.hunt_treasures(minutes_left)
                elif task['name'] == 'sell':
                    if not self.bot.connected:
                        self.bot.interface.connect()
                    self.llf.log(self.bot, '[Scheduler {}] Starting to sell items'.format(
                        self.bot.id) + self.bot.interface.end_color)
                    self.drop_to_bank('all', True)
                    self.sell_all(self.bot.subscribed)
                elif task['name'] == 'sleep':
                    self.llf.log(self.bot, '[Scheduler {}] Sleeping for {} minutes'.format(
                        self.bot.id, round(minutes_left)) + self.bot.interface.end_color)
                    self.bot.interface.disconnect()
                    time.sleep(60 * minutes_left)

    def drop_bot_mobile(self, idx):
        self.goto((-32, 37), 271)
        self.bot.interface.dismount_dd()
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
        if len(dds_paddock) == 10:
            self.bot.interface.put_dd_in_stable(dds_paddock[0].id, 'paddock')
        self.bot.interface.put_dd_in_paddock(idx, 'equip')
        self.bot.interface.close_dd()
        self.bot.mount = 'resting'
        self.llf.set_mount_situation(self.bot.credentials['name'], 'resting')
        self.bot.occupation = 'Putting Bot-Mobile in paddock'
        self.update_db()

    def fetch_bot_mobile(self):
        self.bot.occupation = 'Fetching Bot-Mobile'
        self.update_db()
        self.goto((-32, 37), 271)
        all_dds = self.bot.interface.open_dd()
        bm_id = self.llf.get_bot_mobile(all_dds)
        if bm_id:
            self.bot.interface.equip_dd(bm_id, 'paddock')
        self.bot.interface.close_dd()
        self.bot.interface.mount_dd()
        self.bot.mount = 'equipped'
        self.llf.set_mount_situation(self.bot.credentials['name'], 'equipped')

    def update_db(self):
        try:
            self.llf.log(self.bot,
                         '[Database {}] Uploading {}, {}, {}, {}, {}, {}, {}, {}'.format(self.bot.id, self.bot.id,
                                                                                         self.bot.credentials['server'],
                                                                                         self.bot.credentials['name'],
                                                                                         self.bot.kamas, self.bot.level,
                                                                                         self.bot.occupation,
                                                                                         self.bot.position[0],
                                                                                         self.bot.position[1]))
            self.llf.update_db(
                self.bot.id,
                self.bot.credentials['server'],
                self.bot.credentials['name'],
                self.bot.kamas,
                self.bot.level,
                self.bot.occupation,
                self.bot.position[0],
                self.bot.position[1]
            )
        except TypeError:
            # Degraded upload
            self.llf.log(self.bot, '[Database {}] Uploading {}, {}, {}'.format(self.bot.id, self.bot.id, self.bot.credentials['server'], self.bot.credentials['name']))
            self.llf.update_db(
                self.bot.id,
                self.bot.credentials['server'],
                self.bot.credentials['name'],
                self.bot.kamas,
                self.bot.level,
                self.bot.occupation
            )
        except Exception:
            with open('..//Utils//DatabaseErrorLog.txt', 'a') as f:
                f.write('\n\n' + str(datetime.datetime.now()) + '\n')
                f.write(traceback.format_exc())


__author__ = 'Alexis'
