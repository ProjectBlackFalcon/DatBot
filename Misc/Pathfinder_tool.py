from Model.Pathfinder import PathFinder
import time
import numpy as np
import tkinter
import ast
from PIL import Image, ImageTk

class tool:
    def __init__(self):
        self.tk = tkinter.Tk()
        self.tk.geometry('{}x{}'.format(500, 500))
        self.tk.title('Pathfinder tool')
        self.frame = tkinter.Frame(self.tk, width=500, height=500)
        self.frame.place(x=0, y=0, anchor='nw')

        self.start_lbl = tkinter.Label(self.frame, text='Start')
        self.start_lbl.place(relx=0.20, rely=0.015, anchor='center')
        self.start_coords_lbl = tkinter.Label(self.frame, text='Coords')
        self.start_coords_lbl.place(relx=0.10, rely=0.05, anchor='center')
        self.start_cell_lbl = tkinter.Label(self.frame, text='Cell')
        self.start_cell_lbl.place(relx=0.30, rely=0.05, anchor='center')
        self.start_coords_entry = tkinter.Entry(width=10)
        self.start_coords_entry.place(relx=0.1, rely=0.1, anchor='center')
        self.start_cell_entry = tkinter.Entry(width=3)
        self.start_cell_entry.place(relx=0.30, rely=0.1, anchor='center')

        self.end_lbl = tkinter.Label(self.frame, text='End')
        self.end_lbl.place(relx=0.80, rely=0.015, anchor='center')
        self.end_coords_lbl = tkinter.Label(self.frame, text='Coords')
        self.end_coords_lbl.place(relx=0.7, rely=0.05, anchor='center')
        self.end_cell_lbl = tkinter.Label(self.frame, text='Cell')
        self.end_cell_lbl.place(relx=0.9, rely=0.05, anchor='center')
        self.end_coords_entry = tkinter.Entry(width=10)
        self.end_coords_entry.place(relx=0.7, rely=0.1, anchor='center')
        self.end_cell_entry = tkinter.Entry(width=3)
        self.end_cell_entry.place(relx=0.9, rely=0.1, anchor='center')

        self.path_button = tkinter.Button(command=self.generate_path, text='Get path')
        self.path_button.place(relx=0.33, rely=0.15, anchor='center')

        self.maps_button = tkinter.Button(command=self.generate_map_collage, text='Get maps')
        self.maps_button.place(relx=0.66, rely=0.15, anchor='center')

        self.tk.mainloop()

    def eval_entry(self):
        start_coords = ast.literal_eval(self.start_coords_entry.get())
        start_cell = self.start_cell_entry.get()
        start_cell = int(start_cell) if start_cell else None
        end_coords = ast.literal_eval(self.end_coords_entry.get())
        end_cell = self.end_cell_entry.get()
        end_cell = int(end_cell) if end_cell else None
        return start_coords, start_cell, end_coords, end_cell

    def generate_path(self):
        start_coords, start_cell, end_coords, end_cell = self.eval_entry()
        pf = PathFinder(start_coords, end_coords, start_cell, end_cell, worldmap=1)

        pf.get_path()
        pf.add_path_to_adapted_maps()
        pf.add_map_change_coords_to_adapted_maps()
        pf.map_to_image(pf.adapted_maps, 1)
        lel = np.divide(np.array(Image.open('Out.png')), 255)
        image = Image.fromarray(lel)
        image = image.resize((350, 350))
        photo = ImageTk.PhotoImage(image)
        image_lbl = tkinter.Label(self.tk, image=photo)
        image_lbl.image = photo
        image_lbl.place(relx=0.5, rely=0.6, anchor='center')
        map_change_cells = pf.get_map_change_cells()
        print(pf.path_cells)
        print(map_change_cells)
        return pf.path_cells, map_change_cells

    def generate_map_collage(self):
        start_coords, start_cell, end_coords, end_cell = self.eval_entry()
        pf = PathFinder(start_coords, end_coords, start_cell, end_cell, worldmap=1)

        maps_coords = pf.get_maps_coords()
        maps = []
        shape = (abs(end_coords[1] - start_coords[1]) + 1, abs(end_coords[0] - start_coords[0]) + 1)
        counter = 0
        for coord in maps_coords:
            map_infos = pf.llf.coord_fetch_map(coord, pf.worldmap)
            counter += 1
            print('{}/{}'.format(counter, shape[0] * shape[1]))
            if map_infos is not None and np.array(map_infos).shape == (40, 14):
                maps.append(map_infos)
            elif map_infos is not None and np.array(map_infos).shape != (40, 14):
                maps.append([[5] * 14] * 40)
            else:
                maps.append([[1] * 14] * 40)
        glued = pf.glue_maps(maps, shape)
        # print(glued)
        # print(pf.adapted_maps)
        pf.map_to_image(pf.adapt_shape_maps(glued), 1)
        lel = np.divide(np.array(Image.open('Out.png')), 255)
        image = Image.fromarray(lel)
        image = image.resize((350, 350))
        photo = ImageTk.PhotoImage(image)
        image_lbl = tkinter.Label(self.tk, image=photo)
        image_lbl.image = photo
        image_lbl.place(relx=0.5, rely=0.6, anchor='center')

tool()
"(7, -6), cell 546 to map [6, -6], cell 391, worldmap : 1"
"(9, 25) cell 321 to [8, 25] cell 335"