from Interface import PipeToJava
from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
import unittest
import warnings


class connection(unittest.TestCase):
    def setUp(self):
        warnings.simplefilter("ignore")
        credentials = {'username': 'wublel2', 'password': 'notabot0', 'name': 'Gradopr', 'server': 'Julith'}
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, credentials, llf, True)

    def test_connect(self):
        self.assertEqual([True], self.bot.interface.connect())
        self.assertEqual(True, self.bot.connected)

    def test_disconnect(self):
        self.assertEqual([True], self.bot.interface.disconnect())
        self.assertEqual(False, self.bot.connected)

    def tearDown(self):
        self.pipe.p.terminate()


class move_around(unittest.TestCase):
    def setUp(self):
        warnings.simplefilter("ignore")
        credentials = {'username': 'wublel2', 'password': 'notabot0', 'name': 'Gradopr', 'server': 'Julith'}
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, credentials, llf, True)
        self.bot.interface.connect(max_tries=1)

    def test_get_map(self):
        map = self.bot.interface.get_map()
        self.assertEqual(tuple, type(map))
        self.assertEqual(4, len(map))

    def test_move(self):
        current_map, current_cell, current_worldmap, map_id = self.bot.interface.get_map()
        selected_cell = self.bot.llf.get_closest_walkable_neighbour_cell(current_cell, current_cell, current_map, current_worldmap)
        self.assertEqual([True], self.bot.interface.move(selected_cell))
        self.assertEqual([False], self.bot.interface.move(-1))

    def test_change_map(self):
        current_map, current_cell, current_wor

    def tearDown(self):
        self.pipe.p.terminate()


if __name__ == '__main__':
    runner = unittest.TextTestRunner()
    runner.run(connection())
    # runner.run(move_around())


__author__ = 'Alexis'
