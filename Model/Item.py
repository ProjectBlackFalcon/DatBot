import json
import zlib
from random import randint


class Item:
    def __init__(self, resources, item_stats=None, item_price=-1, item_id=None, level=None, creation_price=-1, resource_prices=None):
        self.all_stats = ['PM', 'Résistance Terre JCJ', 'Résistance Eau JCJ', 'Résistance Air JCJ', 'Résistance Feu JCJ', 'Résistance Neutre JCJ', 'Puissance', 'Sagesse', 'Dommages Neutre', 'Retrait PA', 'Retrait PM', 'Pods', 'Esquive PA', 'Esquive PM', 'Dommages Critiques', 'Résistance Critiques', 'Dommages Terre', 'Résistance Eau', 'Dommages Feu', 'Dommages', 'Dommages Eau', 'Dommages Air', 'Résistance Terre', 'Initiative', 'Vitalité', 'Prospection', 'Soins', '% Critique', 'Dommages Poussée', 'Invocations', '% Dommages distance', 'Résistance Poussée', '% Dommages mêlée', '% Résistance distance', 'Intelligence', '% Résistance Terre', '% Résistance Eau', '% Résistance Air', '% Résistance Feu', '% Résistance Neutre', '% Résistance mêlée', 'Dommages Pièges', 'Chance', '% Résistance Air JCJ', 'Vie', 'PA', 'Fuite', 'Tacle', 'Résistance Air', 'Résistance Feu', 'Résistance Neutre', 'Portée', 'Force', 'Agilité', "% Dommages d'armes", '% Résistance Terre JCJ', '% Résistance Eau JCJ', '% Dommages aux sorts', '% Résistance Feu JCJ', '% Résistance Neutre JCJ']

        self.resources = resources
        self.fm_stats = None
        self.stats = self.stats2dict(item_stats) if type(item_stats) == list else item_stats
        self.level = resources.equipments[str(item_id)]['Level'] if level is not None else None
        self.creation_price = creation_price
        self.resource_prices = resource_prices
        self.item_name = resources.id2names[str(item_id)] if item_id is not None else ''
        self.price = item_price
        self.item_id = item_id
        self.potential_stats = resources.equipments[str(item_id)] if item_id is not None else None
        self.runes_stats = resources.runes_stats
        self.coeff = 100

    def __str__(self):
        return '{} | '.format(self.item_name) + ''.join(['{} : {} | '.format(stat, self.stats[stat]) for stat in self.all_stats])

    def __hash__(self):
        return zlib.adler32(bytes(self.__str__(), 'utf-8'))

    def generate_random_instance(self):
        if self.item_id is None:
            raise ValueError('Item id must be specified')
        generated_stat_list = [(stat, randint(mn, mx)) for stat, mn, mx in self.potential_stats['Stats']]
        self.level = self.resources.equipments[str(self.item_id)]['Level']
        self.stats2dict(generated_stat_list)

    def stats2dict(self, stats):
        stats = [stat for stat in stats if len(stat) == 2 and stat[0] != 740]
        #                                                                         Look at this shit
        output = {self.resources.effect_id2name[str(stat)]: int(value*(-2*(('-' == self.resources.effect_id2name[str(stat)][0])-0.5))) for stat, value in stats if type(value) is not list and len(self.resources.effect_id2name[str(stat)])}
        for stat in self.resources.effect_id2name.values():
            if stat not in output.keys():
                output[stat] = 0
        self.stats = output
        self.fmable_stats()
        return self.stats

    def fmable_stats(self):
        if type(self.stats) is dict:
            self.fm_stats = {stat: value for stat, value in self.stats.items() if stat in self.resources.fmable_stats.keys()}
            return self.fm_stats

    def get_rune_weights(self):
        return {stat: self.runes_stats[stat][0] if stat not in ['Vitalité', 'Pods', 'Initiative'] else 1 for stat, value in self.fm_stats.items() if self.fm_stats[stat]}

    def get_local_focus_weights(self):
        return {stat: value * self.runes_stats[stat][0] for stat, value in self.fm_stats.items() if value}

    def get_min_coeff(self, rune_prices):
        with_focus, no_focus = self.get_runes_obtained()
        rune_prices = rune_prices[~rune_prices['Name'].str.contains(' Pa | Ra | de ')]
        stat_price = {stat: rune_prices.loc[rune, 'PriceAvg'] for rune in rune_prices.index.tolist() for stat, (_, runes_ids) in self.resources.fmable_stats.items() if rune in runes_ids}
        max_value = sum([stat_price[rune]*qty for rune, qty in no_focus.items()])
        focus = 'No focus'

        for rune, number in with_focus.items():
            if stat_price[rune] * number > max_value:
                focus = rune
                max_value = stat_price[rune] * number

        min_coeff = round(100 * self.creation_price / max_value)
        return min_coeff, focus

    def get_runes_obtained(self):
        if self.level == -1:
            raise ValueError('Level must be specified')
        weights = self.get_rune_weights()
        local_focus_weights = self.get_local_focus_weights()
        total_weight = sum([value * self.runes_stats[stat][0] for stat, value in self.fm_stats.items()])

        with_focus = {stat: ((value+(total_weight-value)/2)/weights[stat])*(self.level*0.025)*(self.coeff/100)*0.55 for stat, value in local_focus_weights.items()}
        with_focus = {stat: int(value) if stat in ['PA', 'PM', 'Portée'] else round(value, 1) for stat, value in with_focus.items()}

        no_focus = {stat: round((value/weights[stat])*(self.level*0.025)*(self.coeff/100)*0.55, 1) for stat, value in local_focus_weights.items()}
        no_focus = {stat: int(value) if stat in ['PA', 'PM', 'Portée'] else round(value, 1) for stat, value in no_focus.items()}

        return with_focus, no_focus


if __name__ == '__main__':
    pass
