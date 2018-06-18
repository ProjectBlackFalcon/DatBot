from Interface import PipeToJava
from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
import unittest
import warnings


class Connection(unittest.TestCase):
    def setUp(self):
        warnings.simplefilter("ignore")
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, test_account_credentials, llf, True)

    def test_connect(self):
        self.assertEqual([True], self.bot.interface.connect())
        self.assertTrue(self.bot.connected)
        self.assertEqual('Connecting', self.bot.occupation)
        self.assertIsNotNone(self.bot.position)
        self.assertIsNotNone(self.bot.level)
        self.assertIsNotNone(self.bot.kamas)
        self.assertFalse(self.bot.in_fight)
        self.assertIsNone(self.bot.mount)

    def test_disconnect(self):
        self.assertEqual([True], self.bot.interface.disconnect())
        self.assertFalse(self.bot.connected)
        self.assertEqual('Sleeping', self.bot.occupation)

    def tearDown(self):
        self.pipe.p.terminate()


class MoveAround(unittest.TestCase):
    def setUp(self):
        warnings.simplefilter("ignore")
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, test_account_credentials, llf, True)
        self.bot.interface.connect(max_tries=1)

    def test_get_map(self):
        map = self.bot.interface.get_map()
        self.assertEqual((4, -3), map[0])
        self.assertEqual(300, map[1])
        self.assertEqual(2, map[2])

    def test_move(self):
        self.assertEqual([True], self.bot.interface.move(255))
        self.assertEqual([False], self.bot.interface.move(-1))

    def test_change_map(self):
        self.assertEqual([False], self.bot.interface.change_map(255, 'n'))
        self.assertEqual([True], self.bot.interface.change_map(364, 'w'))
        self.assertEqual([True], self.bot.interface.change_map(321, 'e'))

    def test_go_to_astrub(self):
        self.bot.interface.go_to_astrub()
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((6, -19), curr_map)
        self.assertEqual(397, cell)
        self.assertEqual(1, worldmap)

    def test_go_to_incaranm(self):
        self.bot.interface.go_to_incarnam()
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((3, -4), curr_map)
        self.assertEqual(300, cell)
        self.assertEqual(2, worldmap)

    def test_heavenbag(self):
        self.assertEqual([True], self.bot.interface.enter_heavenbag())
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((0, 0), curr_map)
        self.assertEqual(324, cell)
        self.assertEqual(-1, worldmap)

        self.assertEqual([True], self.bot.interface.enter_heavenbag())
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((0, 0), curr_map)
        self.assertEqual(324, cell)
        self.assertEqual(-1, worldmap)

        self.assertEqual([True], self.bot.interface.exit_heavenbag())
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((3, -4), curr_map)
        self.assertEqual(300, cell)
        self.assertEqual(2, worldmap)

        self.assertEqual([True], self.bot.interface.exit_heavenbag())
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((3, -4), curr_map)
        self.assertEqual(300, cell)
        self.assertEqual(2, worldmap)

    def test_zaap(self):
        self.bot.interface.go_to_astrub()
        self.bot.interface.enter_heavenbag()

        self.assertEqual([False], self.bot.interface.use_zaap((0, 0)))
        self.assertEqual([True], self.bot.interface.use_zaap((5, -18)))

        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((5, -18), curr_map)
        self.assertEqual(260, cell)
        self.assertEqual(1, worldmap)

    def tearDown(self):
        self.pipe.p.terminate()


class Bank(unittest.TestCase):
    def setUp(self):
        warnings.simplefilter("ignore")
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, test_account_credentials, llf, False)
        self.bot.interface.connect(max_tries=1)
        self.assertEqual((4, -18), self.bot.position[0])

    def test_enter_bank(self):
        self.bot.interface.enter_bank()
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual(-1, worldmap)

    def test_open_bank(self):
        self.assertEqual(dict, type(self.bot.interface.open_bank()[0]))


    def tearDown(self):
        pass


if __name__ == '__main__':

    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    # INITAL TESTING CONDITIONS FOR TEST SUITE 1
    #
    # Bot pos                         4, -3, cell 300, Incarnam
    # Mount                                                None
    # In fight                                            False
    # Subbed                                    Not necessarily
    # Known zaaps                                        Astrub
    # Level                                                > 10
    # ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    test_account_credentials = {'username': 'wublel2', 'password': 'notabot0', 'name': 'Gradopr', 'server': 'Julith'}
    runner = unittest.TextTestRunner()
    runner.run(Connection())
    # runner.run(move_around())


__author__ = 'Alexis'
