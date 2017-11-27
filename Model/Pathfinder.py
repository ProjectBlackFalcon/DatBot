import json
import numpy as np
from PIL import Image
from heapq import *
import time
from random import randint
from Model.LowLevelFunctions import LowLevelFunctions


class PathFinder:
    def __init__(self, start_map, end_map, start_cell, end_cell, worldmap):
        self.llf = LowLevelFunctions()
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
        self.shape = (abs(self.bbox[1]-self.bbox[3])+1, abs(self.bbox[0]-self.bbox[2])+1)
        self.mapinfo = self.llf.load_map_info()
        self.maps_coords = []
        self.glued_maps = []
        self.path_cells = []
        self.map_change_coords = []
        self.map_change_cells = []
        self.map_change_directions = []
        self.enlargement_n = 0

    def enlarge(self):
        self.enlargement_n += 1
        print('[Pathfinder] Enlarging')
        self.bbox = (
            self.bbox[0]-1,
            self.bbox[1]-1,
            self.bbox[2]+1,
            self.bbox[3]+1
        )
        self.shape = (abs(self.bbox[1]-self.bbox[3])+1, abs(self.bbox[0]-self.bbox[2])+1)
        self.mapinfo = self.load_map_info()
        self.maps_coords = []
        self.glued_maps = []
        self.path_cells = []
        self.map_change_coords = []
        self.map_change_cells = []
        self.map_change_directions = []

    def get_maps_coords(self):
        xn = abs(self.bbox[0]-self.bbox[2])+1
        yn = abs(self.bbox[1]-self.bbox[3])+1
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

    def glue_maps(self, maps_as_arrays, shape):
        if shape[0]*shape[1] != len(maps_as_arrays):
            raise Exception('n_row*n_col is different than the number of arrays given')
        else:
            maps_as_arrays = np.array(maps_as_arrays)
            arr = maps_as_arrays.reshape(shape[0], shape[1], 40, 14)
            out = np.concatenate([np.concatenate([map for map in arr[i]], axis=1) for i in range(shape[0])], axis=0)
            self.glued_maps = out

    def map_to_image(self, map_as_array, scaling_factor):
        print('[Pathfinder] Generating image...')
        map_as_array = np.array(map_as_array)
        map_as_array[map_as_array == 0] = 0
        # map_as_array[map_as_array != 0] = 1
        a = np.kron(map_as_array, np.ones((scaling_factor, scaling_factor))).astype(int)
        a[a == 0] = 255*64
        a[a == 2] = 255*32
        a[a == 3] = 255*128
        a[a == 4] = 255*255
        Image.fromarray(a).save('Out.png')
        print('[Pathfinder] Done')

    def heuristic(self, a, b):
        return (b[0] - a[0]) ** 2 + (b[1] - a[1]) ** 2

    def astar(self, start_pos, goal_pos):
        start = time.time()
        print('[Pathfinder] Generating path...')

        start_pos = start_pos[1], start_pos[0]
        goal_pos = goal_pos[1], goal_pos[0]

        # print(self.glued_maps.shape)

        neighbors = [(0, 1), (0, -1), (1, 0), (-1, 0), (2, 0), (-2, 0)]

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
                self.path_cells = data[:]

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
        if self.path_cells:
            print('[Pathfinder] Done in {}s'.format(round(time.time()-start, 1)))
        else:
            print('[Pathfinder] Unable to get path')
        return False

    def add_path_to_glued_maps(self):
        for x, y in self.path_cells:
            self.glued_maps[x][y] = 3

    def add_map_change_ccords_to_glued_maps(self):
        for x, y in self.map_change_coords:
            self.glued_maps[x][y] = 4

    def get_path_try(self):
        if not self.maps_coords:
            self.get_maps_coords()

        maps_list = []
        for coord in self.maps_coords:
            map_infos = self.llf.coord_fetch_map(coord, self.worldmap)
            if map_infos is not None:
                maps_list.append(map_infos)
            else:
                maps_list.append([[1 for i in range(14)] for j in range(40)])
        self.glue_maps(maps_list, self.shape)
        self.map_to_image(self.glued_maps, 10)

        if self.end_cell is None:
            end_map_cells = self.llf.coord_fetch_map('{};{}'.format(self.end[0], self.end[1]), self.worldmap)
            found_walkable = False
            while not found_walkable:
                x = randint(0, 13)
                y = randint(0, 39)
                if end_map_cells[y][x] == 0:
                    found_walkable = True
            self.end_cell = self.coord2cell((x, y))

        start_pos = (
            self.cell2coord(self.start_cell)[0]+14*(self.start[0]-self.bbox[0]),
            self.cell2coord(self.start_cell)[1]+40*(self.start[1]-self.bbox[1])
        )
        goal_pos = (
            self.cell2coord(self.end_cell)[0]+14*(self.end[0]-self.bbox[0]),
            self.cell2coord(self.end_cell)[1]+40*(self.end[1]-self.bbox[1])
        )

        # print(start_pos, goal_pos)
        self.astar(goal_pos, start_pos)

    def get_path(self):
        self.get_path_try()
        print(self.path_cells)
        while not self.path_cells and self.enlargement_n < 9:
            self.enlarge()
            self.get_path_try()
        if not self.path_cells:
            raise Exception('Could not generate path')

    def get_map_change_coords(self):
        if not self.path_cells:
            self.get_path()

        out = []
        for i in range(len(self.path_cells)-1):
            this_step = self.path_cells[i]
            next_step = self.path_cells[i+1]
            if (this_step[1]+1) % 14 == 0 and next_step[1] % 14 == 0 or\
                this_step[1] % 14 == 0 and (next_step[1]+1) % 14 == 0 or \
                (this_step[0]+1) % 40 == 0 and next_step[0] % 40 == 0 or \
                this_step[0] % 40 == 0 and (next_step[0]+1) % 40 == 0:
                out.append((this_step[0], this_step[1]))

        marked = []
        for i in range(len(out)-2, -1, -1):
            if abs(out[i][0]-out[i+1][0])+abs(out[i][1]-out[i+1][1]) <= 1:
                marked.append(i)
        for ind in marked:
            # del out[ind]
            pass
        self.map_change_coords = out[:]
        return out[:]

    def get_map_change_cells(self):
        if not self.map_change_coords:
            self.get_map_change_coords()
        out = [self.coord2cell(coord) for coord in self.get_map_change_coords()]
        self.map_change_cells = out[:]
        return out[:]

    def get_directions(self):
        directions = []
        for change_map_cell in self.map_change_coords:
            current = change_map_cell
            nxt = self.path_cells[self.path_cells.index(change_map_cell)+1]
            directions.append(['e', 'w', 's', 'n'][[(0, 1), (0, -1), (1, 0), (-1, 0)].index((nxt[0]-current[0], nxt[1]-current[1]))])
        self.map_change_directions = directions[:]
        return directions[:]

    def cell2coord(self, cell):
        return (cell % 14), cell//14

    def coord2cell(self, coord):
        coord = coord[1] % 14, coord[0] % 40
        return coord[1]*14+coord[0]

__author__ = 'Alexis'
