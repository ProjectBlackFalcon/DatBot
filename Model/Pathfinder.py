import json
import numpy as np
from PIL import Image
from heapq import *
import time

class PathFinder:
    def __init__(self, start_map, end_map, start_cell, end_cell, worldmap):
        self.start = start_map
        self.end = end_map
        self.bbox = (
            min(start_map[0], end_map[0]),
            min(start_map[1], end_map[1]),
            max(start_map[0], end_map[0]),
            max(start_map[1], end_map[1])
        )
        self.start_cell = start_cell
        self.end_cell = end_cell
        self.worldmap = worldmap
        self.shape = (abs(self.end[1]-self.start[1])+1, abs(self.end[0]-self.start[0])+1)
        self.mapinfo = self.load_map_info()
        self.maps_coords = []
        self.glued_maps = []
        self.path = []
        self.map_change_coords = []
        self.map_change_cells = []

    def load_map_info(self):
        with open('..//MapInfo.json', 'r') as f:
            mapinfo = json.load(f)
        return mapinfo

    def get_maps_coords(self):
        xn = abs(self.end[0]-self.start[0])+1
        yn = abs(self.end[1]-self.start[1])+1
        maps = []
        for y in range(yn):
            for x in range(xn):
                maps.append('{};{}'.format(self.bbox[0]+x, self.bbox[1]+y))
        sorted(maps, key=lambda p: [p[1], p[0]])
        self.maps_coords = maps[:]
        return maps[:]

    def id_fetch_map(self, id):
        for map in self.mapinfo:
            if map['id'] == id:
                return map

    def coord_fetch_map(self, coord):
        # print('Fetching : {}'.format(coord))
        for map in self.mapinfo:
            if map['coord'] == coord and map['worldMap'] == self.worldmap and map['hasPriorityOnWorldMap']:
                return map

    def glue_maps(self, maps_as_arrays, shape):
        if shape[0]*shape[1] != len(maps_as_arrays):
            raise Exception('n_row*n_col is different than the number of arrays given')
        else:
            maps_as_arrays = np.array(maps_as_arrays)
            arr = maps_as_arrays.reshape(shape[0], shape[1], 40, 14)
            out = np.concatenate([np.concatenate([map for map in arr[i]], axis=1) for i in range(shape[0])], axis=0)
            self.glued_maps = out

    def map_to_image(self, map_as_array, scaling_factor):
        print('generating image...')
        map_as_array = np.array(map_as_array)
        map_as_array[map_as_array == 0] = 0
        # map_as_array[map_as_array != 0] = 1
        a = np.kron(map_as_array, np.ones((scaling_factor, scaling_factor))).astype(int)
        a[a == 0] = 255*64
        a[a == 2] = 255*32
        a[a == 3] = 255*128
        a[a == 4] = 255*255
        Image.fromarray(a).save('Out.png')
        print('Done')

    def heuristic(self, a, b):
        return (b[0] - a[0]) ** 2 + (b[1] - a[1]) ** 2

    def astar(self, start_pos, goal_pos):
        start = time.time()
        print('generating path...')

        start_pos = start_pos[1], start_pos[0]
        goal_pos = goal_pos[1], goal_pos[0]

        # print(self.glued_maps.shape)

        neighbors = [(0, 1), (0, -1), (1, 0), (-1, 0)]

        close_set = set()
        came_from = {}
        gscore = {start_pos: 0}
        fscore = {start_pos: self.heuristic(start_pos, goal_pos)}
        oheap = []

        heappush(oheap, (fscore[start_pos], start_pos))

        while oheap:

            current = heappop(oheap)[1]

            if current == goal_pos:
                data = []
                while current in came_from:
                    data.append(current)
                    current = came_from[current]
                self.path = data[:]

            close_set.add(current)
            for i, j in neighbors:
                neighbor = current[0] + i, current[1] + j
                tentative_g_score = gscore[current] + self.heuristic(current, neighbor)
                if 0 <= neighbor[0] < self.glued_maps.shape[0]:
                    if 0 <= neighbor[1] < self.glued_maps.shape[1]:
                        if self.glued_maps[neighbor[0]][neighbor[1]] != 0:
                            continue
                    else:
                        # array bound y walls
                        continue
                else:
                    # array bound x walls
                    continue

                if neighbor in close_set and tentative_g_score >= gscore.get(neighbor, 0):
                    continue

                if tentative_g_score < gscore.get(neighbor, 0) or neighbor not in [i[1]for i in oheap]:
                    came_from[neighbor] = current
                    gscore[neighbor] = tentative_g_score
                    fscore[neighbor] = tentative_g_score + self.heuristic(neighbor, goal_pos)
                    heappush(oheap, (fscore[neighbor], neighbor))
        print('Done in {}s'.format(round(time.time()-start, 1)))
        return False

    def add_path_to_glued_maps(self):
        for x, y in self.path:
            self.glued_maps[x][y] = 3

    def add_map_change_ccords_to_glued_maps(self):
        for x, y in self.map_change_coords:
            self.glued_maps[x][y] = 4

    def get_path(self):
        if not self.maps_coords:
            self.get_maps_coords()

        maps_list = []
        for coord in self.maps_coords:
            maps_list.append(self.coord_fetch_map(coord)['cells'])
        self.glue_maps(maps_list, self.shape)

        start_pos = (
            self.cell2coord(self.start_cell)[0]+14*(self.start[0]-self.bbox[0]),
            self.cell2coord(self.start_cell)[1]+40*(self.start[1]-self.bbox[1])
        )
        goal_pos = (
            self.cell2coord(self.end_cell)[0]+14*(self.end[0]-self.bbox[0]),
            self.cell2coord(self.end_cell)[1]+40*(self.end[1]-self.bbox[1])
        )

        # print(start_pos, goal_pos, self.shape)
        self.astar(start_pos, goal_pos)

    def get_map_change_coords(self):
        if not self.path:
            self.get_path()
        out = []
        for x, y in self.path:
            # Going SE
            if self.start[0] < self.end[0] and self.start[1] < self.end[1]:
                if not (y+1) % 14 or not (x+1) % 40:
                    out.append((x, y))
            # Going SW
            if self.start[0] < self.end[0] and self.start[1] > self.end[1]:
                if not x % 14 or not (x+1) % 40:
                    out.append((x, y))
            # Going NE
            if self.start[0] > self.end[0] and self.start[1] < self.end[1]:
                if not (y+1) % 14 or not x % 40:
                    out.append((x, y))
            # Going NW
            if self.start[0] > self.end[0] and self.start[1] > self.end[1]:
                if not y % 14 or not x % 40:
                    out.append((x, y))

        marked = []
        for i in range(len(out)-2, -1, -1):
            if abs(out[i][0]-out[i-1][0])+abs(out[i][1]-out[i-1][1]) <= 1:
                marked.append(i)
        for ind in marked:
            del out[ind]
        self.map_change_coords = out[:]
        return out[:]

    def get_map_change_cells(self):
        if not self.map_change_coords:
            self.get_map_change_coords()
        out = [self.coord2cell(coord) for coord in self.get_map_change_coords()]
        self.map_change_cells = out[:]
        return out[:]

    def cell2coord(self, cell):
        return (cell % 14), cell//14

    def coord2cell(self, coord):
        coord = coord[1] % 14, coord[0] % 40
        return coord[1]*14+coord[0]

__author__ = 'Alexis'
