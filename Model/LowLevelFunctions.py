class LowLevelFunctions:
    def __init__(self):
        pass

    def cell2coord(self, cell):
        return (cell % 14), cell//14

    def coord2cell(self, coord):
        coord = coord[1] % 14, coord[0] % 40
        return coord[1]*14+coord[0]

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

__author__ = 'Alexis'
