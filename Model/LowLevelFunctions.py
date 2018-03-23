import json
import mysql.connector
import copy
import ast
import datetime


class LowLevelFunctions:
    def __init__(self, map_info=None):
        self.map_info = [] if map_info is None else map_info
        self.disc_zaaps = self.get_discovered_zaaps()
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

    def update_db(self, bot_id, server, name, kamas, level, occupation, current_map='OFFLINE', worldmap=1):
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

        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""UPDATE BotAccounts SET zaaps='{}' WHERE name='{}'""".format(self.disc_zaaps[bot_name], bot_name))
        conn.commit()
        conn.close()

    def get_discovered_zaaps(self, bot_name=None):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")

        cursor = conn.cursor()
        if bot_name is not None:
            cursor.execute("""SELECT zaaps FROM BotAccounts WHERE name='{}'""".format(bot_name))
            conn.close()
            zaaps = []
            for row in cursor:
                zaaps = row[0]
            if zaaps:
                zaaps = ast.literal_eval(zaaps)
            else:
                zaaps = []
            self.disc_zaaps[bot_name] = copy.deepcopy(zaaps)
            return zaaps
        else:
            cursor.execute("""SELECT zaaps, name FROM BotAccounts""")
            conn.close()
            zaaps = {}
            for row in cursor:
                if row[0]:
                    zaaps[row[1]] = ast.literal_eval(row[0])
                else:
                    zaaps[row[1]] = []
            return copy.deepcopy(zaaps)

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
            self.disc_zaaps[bot_name] = self.get_discovered_zaaps(bot_name)

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
            if dd.name == 'Bot-Mobile':
                dd.score += 100

    def get_bot_mobile(self, dd_list):
        bm_id = False
        for dd in dd_list:
            if dd['name'] == 'Bot-Mobile':
                bm_id = dd['id']
        return bm_id

    def get_schedule(self, bot_name):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec", database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""SELECT schedule FROM BotAccounts WHERE name='{}'""".format(bot_name))
        schedule = ''
        conn.close()
        for row in cursor:
            schedule = row[0]
        if schedule:
            schedules = [ast.literal_eval(schedule)]
        else:
            with open('..//Utils//Schedules//default.json', 'r') as f:
                schedules = json.load(f)

        schedule = []
        for schedule_curr in schedules:
            if schedule_curr['idx'] == 0:
                schedule = schedule_curr['tasks']
        if schedule:
            return schedule
        else:
            raise RuntimeError('Error fetching schedule')

    def get_mount_situation(self, bot_name):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""SELECT mount FROM BotAccounts WHERE Name='{}'""".format(bot_name))
        conn.close()
        mount_situation = ''
        for row in cursor:
            mount_situation = row[0] if row[0] else 'None'
        return mount_situation

    def set_mount_situation(self, bot_name, situation):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""UPDATE BotAccounts SET mount='{}' WHERE name='{}'""".format(situation, bot_name))
        conn.commit()
        conn.close()

    def log(self, bot, message):
        name = bot.credentials['name']
        color = bot.interface.color
        print(color + message + '\033[0m')
        with open('..//Utils//BotsLogs//{}.txt'.format(name), 'a') as f:
            f.write(str(datetime.datetime.now()) + '  ' + message + '\n')
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        try:
            cursor.execute("""UPDATE BotAccounts SET position='{}' WHERE name='{}'""".format(list(bot.position[0]), name))
        except TypeError as e:
            print("Not uploading that")
        conn.commit()
        conn.close()

    def dds_to_db(self, bot_name, dds):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        db_dd_ids = set()
        cursor.execute("""SELECT dd_id FROM DDs""")
        for row in cursor:
            db_dd_ids.add(row[0])

        for dd in dds:
            preg = True if dd.fecondation_time != -1 else False
            if dd.id in db_dd_ids:
                cursor.execute("""UPDATE DDs SET fec='{}', preg='{}', sterile='{}', fatigue='{}' WHERE dd_id='{}'""".format(int(dd.is_fecondation_ready), int(preg), int(dd.sterile), dd.fatigue, dd.id))
            else:
                cursor.execute("""INSERT INTO DDs (id, dd_id, owner, sex, fec, preg, repro, sterile, fatigue) VALUES (NULL, '{}', '{}', '{}', '{}', '{}', '{}', '{}', '{}')""".format(dd.id, bot_name, dd.sex, int(dd.is_fecondation_ready), int(preg), int('Reproductrice' in dd.behaviours), int(dd.sterile), dd.fatigue))
        conn.commit()
        conn.close()

    def hunts_to_db(self, bot_name, duration, success, reason=''):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""INSERT INTO Hunts (bot, success, reason, duration) VALUES ('{}', '{}', '{}', '{}')""".format(bot_name, int(success), reason, duration))
        conn.commit()
        conn.close()

    def fetch_harvest_path(self, bot_name):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""SELECT * FROM HarvestPaths""")
        conn.close()
        assigned_path = None
        default_path = None
        for row in cursor:
            if row[0] == 1:
                default_path = ast.literal_eval(row[2])
            if bot_name in row[3]:
                assigned_path = ast.literal_eval(row[2])
        if assigned_path is None:
            assigned_path = default_path

        return assigned_path

__author__ = 'Alexis'
