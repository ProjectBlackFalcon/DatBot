import json


class LowLevelFunctions:
    def __init__(self):
        pass

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
            if self.flatten_map(self.coord_fetch_map(map_coords, worldmap))[neighbour] == 0:
                walkable_neighbours.append(neighbour)
        return walkable_neighbours[:]

    def get_closest_walkable_neighbour_cells(self, target_cell, player_cell, map_coords, worldmap):
        walkable_neighbours = self.get_walkable_neighbour_cells(target_cell, map_coords, worldmap)
        closest = walkable_neighbours[0], self.distance_cell(walkable_neighbours[0], player_cell)
        for walkable_neighbour in walkable_neighbours:
            if self.distance_cell(walkable_neighbour, player_cell) < closest[1]:
                closest = walkable_neighbour, self.distance_cell(walkable_neighbour, player_cell)
        return closest[0]

    def get_closest_walkable_cell(self, target_cell, map_coords, worldmap):
        map_info = self.flatten_map(self.coord_fetch_map(map_coords, worldmap))
        closest = None, 2000
        for n_tile in range(len(map_info)):
            if self.distance_cell(target_cell, n_tile) < closest[1] and map_info[n_tile] == 0:
                closest = n_tile, self.distance_cell(target_cell, n_tile)
        return closest[0]

    def load_map_info(self):
        with open('..//MapInfo.json', 'r') as f:
            mapinfo = json.load(f)
        return mapinfo

    def coord_fetch_map(self, coord, worldmap):
        # print('Fetching : {}'.format(coord))
        maps = []
        for map in self.load_map_info():
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

__author__ = 'Alexis'
