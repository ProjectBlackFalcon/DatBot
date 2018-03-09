import json
import mysql.connector
import numpy as np
import copy


class LowLevelFunctions:
    def __init__(self, map_info=None):
        self.map_info = [] if map_info is None else map_info
        with open('..//Utils//discoveredZaaps.json', 'r') as f:
            self.disc_zaaps = json.load(f)
        with open('..//Utils//zaapList.json', 'r') as f:
            self.zaaps = json.load(f)

    def cell2coord(self, cell):
        return cell % 14 + int((cell//14)/2+0.5), (13 - cell % 14 + int((cell//14)/2))

    def coord2cell(self, coord):
        i = 0
        result = self.cell2coord(i)
        while result != coord:
            i += 1
            result = self.cell2coord(i)
        return i

    def distance_coords(self, coord_1, coord_2):
        return ((coord_2[0]-coord_1[0])**2 + (coord_2[1]-coord_1[1])**2)**0.5

    def distance_cell(self, cell_1, cell_2):
        return self.distance_coords(self.cell2coord(cell_1), self.cell2coord(cell_2))

    def closest_cell(self, cell, cell_list):
        closest = cell_list[0], self.distance_cell(cell, cell_list[0])
        for cell_close in cell_list:
            if self.distance_cell(cell, cell_close) < closest[1]:
                closest = cell_close, self.distance_cell(cell, cell_close)
        return closest[0]

    def get_neighbour_cells(self, cell):
        neighbours = []
        for i in range(560):
            if self.distance_cell(cell, i) == 1:
                neighbours.append(i)
        return neighbours[:]

    def get_walkable_neighbour_cells(self, cell, map_coords, worldmap):
        walkable_neighbours = []
        for neighbour in self.get_neighbour_cells(cell):
            if self.flatten_map(self.coord_fetch_map('{};{}'.format(map_coords[0], map_coords[1]), worldmap))[neighbour] == 0:
                walkable_neighbours.append(neighbour)
        return walkable_neighbours[:]

    def get_closest_walkable_neighbour_cell(self, target_cell, player_cell, map_coords, worldmap):
        walkable_neighbours = self.get_walkable_neighbour_cells(target_cell, map_coords, worldmap)
        if walkable_neighbours:
            closest = walkable_neighbours[0], 10000
        else:
            return False
        for walkable_neighbour in walkable_neighbours:
            if self.distance_cell(walkable_neighbour, player_cell) < closest[1]:
                closest = walkable_neighbour, self.distance_cell(walkable_neighbour, player_cell)

        if closest[1] < 10000:
            return closest[0]
        return False

    def get_closest_walkable_cell(self, target_cell, map_coords, worldmap):
        map_info = self.flatten_map(self.coord_fetch_map('{};{}'.format(map_coords[0], map_coords[1]), worldmap))
        closest = None, 2000
        for n_tile in range(len(map_info)):
            if (0 < self.distance_cell(target_cell, n_tile) < closest[1]) and map_info[n_tile] == 0:
                closest = n_tile, self.distance_cell(target_cell, n_tile)
        return closest[0]

    def load_map_info(self):
        with open('..//Utils//MapInfo.json', 'r') as f:
            mapinfo = json.load(f)
        return mapinfo

    def coord_fetch_map(self, coord, worldmap):
        # print('Fetching : {}'.format(coord))
        if not self.map_info:
            self.map_info = self.load_map_info()
        maps = []
        for map in self.map_info:
            if map['coord'] == coord and map['worldMap'] == worldmap:
                maps.append(map)
        if len(maps) == 1 and maps[0] is not None:
            return maps[0]['cells']
        elif len(maps) > 1:
            for map in maps:
                if map['hasPriorityOnWorldMap']:
                    return map['cells']

    def flatten_map(self, map):
        flattened = []
        for line in map:
            flattened += line
        return flattened

    def get_closest_statue(self, pos):
        with open('..//Utils//classStatues.json', 'r') as f:
            class_statues = json.load(f)
        closest = None, 100000
        for class_name, statue_pos in class_statues.items():
            if self.distance_coords(pos, statue_pos) < closest[1]:
                closest = statue_pos, self.distance_coords(pos, statue_pos)
        return closest[0]

    def update_db(self, bot_id, server, name, kamas, level, occupation, current_map, worldmap):
        try:
            conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                           database="wz3xj6_spec")
            cursor = conn.cursor()
            put = (bot_id, server, name, kamas, level, occupation, str(current_map), worldmap)
            cursor.execute("""INSERT INTO Bots (BotId, Server, Name, Kamas, Level, Occupation, Pos, Worldmap) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)""", put)
            conn.commit()
            conn.close()
        except Exception:
            print('Could not upload')
            # Eww

    def get_next_clue_pos(self, clue, current_pos, direction):
        with open('..//Utils//TresureHuntClues.json', 'r') as f:
            clues = json.load(f, encoding='latin-1')
        clue_possible_pos = clues[clue.lower()]
        direction_vector = {'n': (0, -1), 's': (0, 1), 'w': (-1, 0), 'e': (1, 0)}[direction]
        found, i, checking_pos = False, 1, current_pos
        while not found and i <= 10:
            checking_pos = [checking_pos[j] + direction_vector[j] for j in range(2)]
            if checking_pos in clue_possible_pos:
                found = checking_pos
            i += 1
        if found:
            return found
        else:
            raise RuntimeError('Non existing clue : {}, going {} from {}'.format(clue, direction, current_pos))

    def add_discovered_zaap(self, bot_name, zaap_pos):
        if list(zaap_pos) in self.zaaps:
            if bot_name in self.disc_zaaps.keys():
                if list(zaap_pos) not in self.disc_zaaps[bot_name]:
                    self.disc_zaaps[bot_name].append(zaap_pos)
            else:
                self.disc_zaaps[bot_name] = [zaap_pos]

        with open('..//Utils//discoveredZaaps.json', 'w') as f:
            json.dump(self.disc_zaaps, f)

    def get_closest_known_zaap(self, bot_name, pos):
        if bot_name in self.disc_zaaps.keys():
            disc_zaaps = self.disc_zaaps[bot_name]
        else:
            return None
        closest = None, 100000
        for zaap_pos in disc_zaaps:
            if self.distance_coords(pos, zaap_pos) < closest[1]:
                closest = zaap_pos, self.distance_coords(pos, zaap_pos)
        return closest[0]

    def get_closest_unknown_zaap(self, bot_name, pos):
        disc_zaaps = []
        if bot_name in self.disc_zaaps.keys():
            disc_zaaps = self.disc_zaaps[bot_name]
        else:
            self.disc_zaaps[bot_name] = []
            with open('..//Utils//discoveredZaaps.json', 'w') as f:
                json.dump(self.disc_zaaps, f)

        zaaps = copy.deepcopy(self.zaaps)

        for disc_zaap in disc_zaaps:
            del zaaps[zaaps.index(disc_zaap)]

        closest = False, 100000
        for zaap_pos in zaaps:
            if self.distance_coords(pos, zaap_pos) < closest[1]:
                closest = zaap_pos, self.distance_coords(pos, zaap_pos)
        return closest[0]

    def get_inventory_id(self, inventory, general_id):
        inv_id = 0
        for item in inventory:
            if item[1] == general_id:
                inv_id = item[2]
        return inv_id

    def get_number_of_item_in_inventory(self, inventory, general_id):
        number = 0
        for item in inventory:
            if item[1] == general_id:
                number = item[3]
        return number

    def get_weight_of_item_in_inventory(self, inventory, general_id):
        weight = 0
        for item in inventory:
            if item[1] == general_id:
                weight = item[5]
        return weight

    def get_brak_maps(self):
        with open('..//Utils//BrakMaps.json', 'r') as f:
            brak_maps = json.load(f)
        return brak_maps

    def get_bwork_maps(self):
        with open('..//Utils//bworkMaps.json', 'r') as f:
            bwork_maps = json.load(f)
        return bwork_maps

    def get_map_dd_tool(self, position):
        with open('..//Utils//ddTools.json', 'r') as f:
            tools = json.load(f)
        return tools[str(tuple(position))]

    def score_dds(self, dd_list):
        n_male, n_female, n_repro = 0, 0, 0
        for dd in dd_list:
            if dd.sex == 'male':
                n_male += 1
            else:
                n_female += 1

        for dd in dd_list:
            dd.score = 0
            if dd.maturity > 0:
                dd.score += 1
            if dd.maturity == 100:
                dd.score += 1
            if dd.level == 5:
                dd.score += 1
            if dd.fecondation_time != -1:
                dd.score += 5
            if dd.is_fecondation_ready:
                dd.score += 5
            if 'Reproductrice' in dd.behaviours:
                # Heavily favor this trait so that eventually all DDs are repro.
                dd.score += 50
            if n_female < n_male and dd.sex == 'female':
                dd.score += 1
            if n_female > n_male and dd.sex == 'male':
                dd.score += 1

__author__ = 'Alexis'
