from Interface import PipeToJava
from LowLevelFunctions import LowLevelFunctions
from Bot import Bot
import unittest
import warnings

test_account_credentials = {'username': 'democraticamnesiac', 'password': 'answerflash2', 'name': 'Maxitreur', 'server': 'Julith'}


class a_Connection(unittest.TestCase):
    @classmethod
    def setUpClass(self):
        warnings.simplefilter("ignore")
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, test_account_credentials, llf, True)

    def test_a_connect(self):
        self.assertEqual(True, self.bot.interface.connect()[0])
        self.assertTrue(self.bot.connected)
        self.assertEqual('Connecting', self.bot.occupation)
        self.assertIsNotNone(self.bot.position)
        self.assertIsNotNone(self.bot.characteristics.level)
        self.assertIsNotNone(self.bot.inventory.kamas)
        self.assertFalse(self.bot.in_fight)
        self.assertIsNone(self.bot.mount)

    def test_b_disconnect(self):
        self.assertEqual(True, self.bot.interface.disconnect()[0])
        self.assertFalse(self.bot.connected)
        self.assertEqual('Sleeping', self.bot.occupation)

    @classmethod
    def tearDownClass(self):
        self.pipe.p.terminate()


class b_MoveAround(unittest.TestCase):
    @classmethod
    def setUpClass(self):
        warnings.simplefilter("ignore")
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, test_account_credentials, llf, True)
        self.bot.interface.connect(max_tries=1)

    def test_a_get_map(self):
        map = self.bot.interface.get_map()
        self.assertEqual((4, -3), map[0])
        self.assertEqual(300, map[1])
        self.assertEqual(2, map[2])

    def test_b_move(self):
        self.assertEqual(True, self.bot.interface.move(255)[0])
        self.assertEqual(False, self.bot.interface.move(171)[0])

    def test_c_change_map(self):
        self.assertEqual(False, self.bot.interface.change_map(255, 'n')[0])
        self.assertEqual(True, self.bot.interface.change_map(364, 'w')[0])
        self.assertEqual(True, self.bot.interface.change_map(321, 'e')[0])

    def test_d_go_to_astrub(self):
        self.bot.interface.go_to_astrub()
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((6, -19), curr_map)
        self.assertEqual(397, cell)
        self.assertEqual(1, worldmap)

    def test_e_heavenbag(self):
        self.assertEqual(True, self.bot.interface.enter_heavenbag()[0])
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((0, 0), curr_map)
        self.assertEqual(324, cell)
        self.assertEqual(-1, worldmap)

        self.assertEqual(True, self.bot.interface.enter_heavenbag()[0])
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((0, 0), curr_map)
        self.assertEqual(324, cell)
        self.assertEqual(-1, worldmap)

        self.assertEqual(True, self.bot.interface.exit_heavenbag()[0])
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((6, -19), curr_map)
        self.assertEqual(397, cell)
        self.assertEqual(1, worldmap)

        self.assertEqual(True, self.bot.interface.exit_heavenbag()[0])
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((6, -19), curr_map)
        self.assertEqual(397, cell)
        self.assertEqual(1, worldmap)

    def test_f_zaap(self):
        self.bot.interface.enter_heavenbag()

        self.assertEqual(False, self.bot.interface.use_zaap((0, 0))[0])
        self.assertEqual(((5,-18), 260, 1, 191105026), self.bot.interface.use_zaap((5, -18)))

        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((5, -18), curr_map)
        self.assertEqual(260, cell)
        self.assertEqual(1, worldmap)

    def test_g_go_to_incaranm(self):
        self.bot.hf.goto((6, -19))
        self.bot.interface.go_to_incarnam()
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual((4, -3), curr_map)
        self.assertEqual(300, cell)
        self.assertEqual(2, worldmap)

    @classmethod
    def tearDownClass(self):
        self.pipe.p.terminate()

'''
class c_Bank(unittest.TestCase):
    @classmethod
    def setUpClass(self):
        warnings.simplefilter("ignore")
        llf = LowLevelFunctions()
        self.pipe = PipeToJava(headless=True)
        self.bot = Bot(self.pipe, 0, test_account_credentials, llf, False)
        self.bot.interface.connect(max_tries=1)
        assert (4, -18) == self.bot.position[0]

    def test_enter_bank(self):
        self.bot.interface.enter_bank()
        curr_map, cell, worldmap, map_id = self.bot.interface.get_map()
        self.assertEqual(-1, worldmap)

    def test_open_bank(self):
        self.assertEqual(dict, type(self.bot.interface.open_bank()[0]))

    @classmethod
    def tearDownClass(self):
        pass
'''

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
    runner = unittest.TextTestRunner()
    runner.run(b_MoveAround)


__author__ = 'Alexis'
