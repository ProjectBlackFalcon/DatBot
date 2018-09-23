import json


class Item:
    def __init__(self, item_stats, level, creation_price, rune_prices):
        self.stats = item_stats
        self.level = level
        self.creation_price = creation_price
        self.rune_prices = rune_prices
        with open('../Utils/RunesStats.json', 'r') as f:
            self.runes_stats = json.load(f)
        self.coeff = 100

    def get_rune_weights(self):
        return {stat: self.runes_stats[stat][0] if stat not in ['Vitalite', 'Pods', 'Initiative'] else 1 for stat, value in self.stats.items() if self.stats[stat]}

    def get_local_focus_weights(self):
        return {stat: value * self.runes_stats[stat][0] for stat, value in self.stats.items() if value}

    def get_min_coeff(self):
        pass

    def get_runes_obtained(self):
        weights = self.get_rune_weights()
        local_focus_weights = self.get_local_focus_weights()
        total_weight = sum([value * self.runes_stats[stat][0] for stat, value in self.stats.items()])

        with_focus = {stat: ((value+(total_weight-value)/2)/weights[stat])*(self.level*0.025)*(self.coeff/100)*0.55 for stat, value in local_focus_weights.items()}
        with_focus = {stat: int(value) if stat in ['PA', 'PM', 'PO'] else round(value, 1) for stat, value in with_focus.items()}

        no_focus = {stat: round((value/weights[stat])*(self.level*0.025)*(self.coeff/100)*0.55, 1) for stat, value in local_focus_weights.items()}
        no_focus = {stat: int(value) if stat in ['PA', 'PM', 'PO'] else round(value, 1) for stat, value in no_focus.items()}

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

    item = Item(item_stats, 84, 1300000, rune_prices)
    print(item.get_runes_obtained())
