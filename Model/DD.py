class DD:
    def __init__(self, dd_stats_dict):
        self.id = dd_stats_dict['id']
        self.behaviours = dd_stats_dict['behaviours']
        self.name = dd_stats_dict['name']
        self.sex = 'female' if dd_stats_dict['sex'] else 'male'
        self.experience = dd_stats_dict['experience']
        self.level = dd_stats_dict['level']
        self.stamina = dd_stats_dict['stamina']*100/dd_stats_dict['staminaMax']
        self.maturity = dd_stats_dict['maturity']*100/dd_stats_dict['maturityForAdult']
        self.energy = dd_stats_dict['energy']*100/dd_stats_dict['energyMax']
        self.serenity = dd_stats_dict['serenity']
        self.love = dd_stats_dict['love']*100/dd_stats_dict['loveMax']
        self.fecondation_time = dd_stats_dict['fecondationTime']
        self.is_fecondation_ready = dd_stats_dict['isFecondationReady']
        self.fatigue = dd_stats_dict['boostLimiter']*100/dd_stats_dict['boostMax']
        self.sterile = dd_stats_dict['reproductionCount']//dd_stats_dict['reproductionCountMax']
        self.in_paddock = dd_stats_dict['inPaddock']
        self.score = 0

    def get_next_spot(self):
        if self.sterile and self.fecondation_time == -1:
            return 'out'
        if self.sterile:
            return 'stable'
        if self.energy == 100 and self.level < 5:
            return 'pex'
        if self.is_fecondation_ready:
            return 'stable'
        if self.serenity > 200 and self.maturity != 100 and self.love != 100:
            return 'love'
        if self.serenity < -200 and self.maturity != 100 and self.stamina != 100:
            return 'stamina'
        if self.serenity > 200 and self.maturity != 100 and self.love == 100:
            return 'mood-'
        if self.serenity < -200 and self.maturity != 100 and self.stamina == 100:
            return 'mood+'
        if self.maturity != 100:
            return 'maturity'
        if self.energy != 100:
            return 'energy'
        if self.stamina != 100 and self.serenity <= -200 and self.sex == 'female':
            return 'stamina'
        if self.stamina != 100 and self.serenity < 0 and self.sex == 'male':
            return 'stamina'
        if self.love != 100 and self.serenity >= 200 and self.sex == 'male':
            return 'love'
        if self.love != 100 and self.serenity > 0 and self.sex == 'female':
            return 'love'
        if self.love != 100 and self.stamina != 100 and 0 <= self.serenity <= 200 and self.sex == 'male':
            return 'mood-'
        if self.love != 100 and self.stamina != 100 and -200 <= self.serenity <= 0 and self.sex == 'female':
            return 'mood+'
        if self.love == 100 and self.sex == 'female' and self.serenity >= -200:
            return 'mood-'
        if self.love == 100 and self.sex == 'male' and self.serenity > 0:
            return 'mood-'
        if self.stamina == 100 and self.sex == 'female' and self.serenity < 0:
            return 'mood+'
        if self.stamina == 100 and self.sex == 'male' and self.serenity <= 200:
            return 'mood+'