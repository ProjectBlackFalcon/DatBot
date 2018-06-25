class Characteristics:
    def __init__(self):
        self.level = None
        self.xp = None
        self.xp_next_level_floor = None
        self.weight = None
        self.weight_max = None
        self.health_percent = None

        self.jobs = None

        self.vi = None
        self.int = None
        self.agi = None
        self.cha = None
        self.fo = None
        self.sa = None
        self.available_stat_points = None

    def get_primary_characs(self):
        names = ['Vi', 'Int', 'Agi', 'Cha', 'Fo', 'Sa', 'Available']
        return dict(zip(names, [self.vi, self.int, self.agi, self.cha, self.fo, self.sa, self.available_stat_points]))
