import json


class Inventory:
    def __init__(self, bot):
        self.bot = bot
        self.kamas = None
        self.items = None

    def item_in_slot(self, slot):
        for item in self.items:
            if item[4] == slot:
                return True
        return False

    def equip_item(self, global_id, slot):
        already_equipped = False
        for item in self.items:
            if item[1] == global_id and item[4] == slot:
                already_equipped = True

        if not already_equipped:
            for item in self.items:
                if item[1] == global_id:
                    if self.item_in_slot(slot):
                        for item_equipped in self.items:
                            if item_equipped[4] == slot:
                                self.bot.interface.de_equip_item(item_equipped[2])
                    self.bot.interface.equip_item(item[2], slot)

    def equip_preferred_stuff(self):
        stuff_level = int(self.bot.characteristics.level/20)*20
        with open('../Utils/Preferred_stuff.json', 'r') as f:
            preferred_stuff = json.load(f)[str(stuff_level)]
        for item in preferred_stuff:
            self.equip_item(item['Id'], item['Slot'])
