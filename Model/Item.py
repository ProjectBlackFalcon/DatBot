import json
import zlib


class Item:
    def __init__(self, bot, item_stats, item_price=-1, item_id=None, level=-1, creation_price=-1, resource_prices=None):
        self.all_stats = ['PM', 'Résistance Terre JCJ', 'Résistance Eau JCJ', 'Résistance Air JCJ', 'Résistance Feu JCJ', 'Résistance Neutre JCJ', 'Puissance', 'Sagesse', 'Dommages Neutre', 'Retrait PA', 'Retrait PM', 'Pods', 'Esquive PA', 'Esquive PM', 'Dommages Critiques', 'Résistance Critiques', 'Dommages Terre', 'Résistance Eau', 'Dommages Feu', 'Dommages', 'Dommages Eau', 'Dommages Air', 'Résistance Terre', 'Initiative', 'Vitalité', 'Prospection', 'Soins', '% Critique', 'Dommages Poussée', 'Invocations', '% Dommages distance', 'Résistance Poussée', '% Dommages mêlée', '% Résistance distance', 'Intelligence', '% Résistance Terre', '% Résistance Eau', '% Résistance Air', '% Résistance Feu', '% Résistance Neutre', '% Résistance mêlée', 'Dommages Pièges', 'Chance', '% Résistance Air JCJ', 'Vie', 'PA', 'Fuite', 'Tacle', 'Résistance Air', 'Résistance Feu', 'Résistance Neutre', 'Portée', 'Force', 'Agilité', "% Dommages d'armes", '% Résistance Terre JCJ', '% Résistance Eau JCJ', '% Dommages aux sorts', '% Résistance Feu JCJ', '% Résistance Neutre JCJ']

        self.bot = bot
        self.stats = item_stats if type(item_stats) == dict else self.stats2dict(item_stats)
        self.level = level
        self.creation_price = creation_price
        self.resource_prices = resource_prices
        self.item_name = bot.resources.id2names[item_id] if item_id is not None else ''
        self.price = item_price
        with open('../Utils/RunesStats.json', 'r') as f:
            self.runes_stats = json.load(f)
        self.coeff = 100

    def __str__(self):
        return '{} | {} |'.format(self.item_name, self.price).join(['{} : {} | '.format(stat, self.stats[stat]) for stat in self.all_stats])

    def __hash__(self):
        return zlib.adler32(bytes(self.__str__(), 'utf-8'))

    def stats2dict(self, stats):
        stats = [stat for stat in stats if len(stat) == 2 and stat[0] != 740]
        #                                                                         Look at this shit
        output = {self.bot.resources.effect_id2name[str(stat)]: int(value*(-2*(('-' == self.bot.resources.effect_id2name[str(stat)][0])-0.5))) for stat, value in stats if type(value) is not list and len(self.bot.resources.effect_id2name[str(stat)])}
        for stat in self.bot.resources.effect_id2name.values():
            if stat not in output.keys():
                output[stat] = 0
        return output

    def get_rune_weights(self):
        return {stat: self.runes_stats[stat][0] if stat not in ['Vitalite', 'Pods', 'Initiative'] else 1 for stat, value in self.stats.items() if self.stats[stat]}

    def get_local_focus_weights(self):
        return {stat: value * self.runes_stats[stat][0] for stat, value in self.stats.items() if value}

    def get_min_coeff(self, rune_prices):
        with_focus, no_focus = self.get_runes_obtained()
        max_value = sum([price[3]*no_focus[rune] for rune, price in rune_prices.items()])
        focus = 'No focus'

        for rune, number in with_focus.items():
            if rune_prices[rune][3] * number > max_value:
                focus = rune
                max_value = rune_prices[rune][3] * number

        min_coeff = self.creation_price / max_value
        return min_coeff, focus

    def get_runes_obtained(self):
        weights = self.get_rune_weights()
        local_focus_weights = self.get_local_focus_weights()
        total_weight = sum([value * self.runes_stats[stat][0] for stat, value in self.stats.items()])

        with_focus = {stat: ((value+(total_weight-value)/2)/weights[stat])*(self.level*0.025)*(self.coeff/100)*0.55 for stat, value in local_focus_weights.items()}
        with_focus = {stat: int(value) if stat in ['PA', 'PM', 'Portée'] else round(value, 1) for stat, value in with_focus.items()}

        no_focus = {stat: round((value/weights[stat])*(self.level*0.025)*(self.coeff/100)*0.55, 1) for stat, value in local_focus_weights.items()}
        no_focus = {stat: int(value) if stat in ['PA', 'PM', 'Portée'] else round(value, 1) for stat, value in no_focus.items()}

        return with_focus, no_focus


if __name__ == '__main__':
    item_stats = {'Intelligence': 24, 'Dommage air': 0, 'Retrait PA': 0, 'Resistance % neutre': 0, 'Resistance PA': 0, 'Sagesse': 21,
     'Resistance % air': 0, 'PM': 0, 'Dommage eau': 0, 'Dommage poussee': 0, 'Puissance': 0, 'Resistance poussee': 0,
     'Retrait PM': 0, 'Renvoi dommage': 0, 'Dommage feu': 0, 'Soin': 0, 'Dommage': 0, 'Vitalite': 32, 'Portee': 0,
     'Tacle': 0, 'Resistance feu': 0, 'Fuite': 0, 'Resistance neutre': 0, 'Resistance critique': 0, 'Rune de chasse': 0,
     'Resistance % eau': 0, 'Dommage critique': 0, 'Dommage piege': 0, 'Dommage % piege': 0,
     'Dommage terre': 0, 'Resistance PM': 0, 'Critique': 0, 'Chance': 0, 'Invocation': 0, 'Resistance eau': 4,
     'Dommage neutre': 0, 'Resistance % feu': 0, 'PA': 1, 'Force': 0, 'Initiative': 0, 'Prospection': 0,
     'Resistance % terre': 0, 'Resistance air': 0, 'Resistance terre': 5, 'Agilite': 17, 'Pods': 0}

    rune_prices = {'Intelligence': 68, 'Dommage air': 0, 'Retrait PA': 0, 'Resistance % neutre': 0, 'Resistance PA': 0, 'Sagesse': 79,
     'Resistance % air': 0, 'PM': 0, 'Dommage eau': 0, 'Dommage poussee': 0, 'Puissance': 0, 'Resistance poussee': 0,
     'Retrait PM': 0, 'Renvoi dommage': 0, 'Dommage feu': 0, 'Soin': 0, 'Dommage': 970, 'Vitalite': 167, 'Portee': 0,
     'Tacle': 0, 'Resistance feu': 0, 'Fuite': 0, 'Resistance neutre': 0, 'Resistance critique': 0, 'Rune de chasse': 0,
     'Resistance % eau': 0, 'Dommage critique': 0, 'Dommage piege': 0, 'Dommage % piege': 0,
     'Dommage terre': 0, 'Resistance PM': 0, 'Critique': 2181, 'Chance': 92, 'Invocation': 0, 'Resistance eau': 0,
     'Dommage neutre': 0, 'Resistance % feu': 0, 'PA': 0, 'Force': 0, 'Initiative': 79, 'Prospection': 149,
     'Resistance % terre': 1080, 'Resistance air': 0, 'Resistance terre': 190, 'Agilite': 0, 'Pods': 0}

    item = Item(item_stats, 84)
    print(str(item))
    print(hash(item))
