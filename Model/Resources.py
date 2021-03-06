import json


class Resources:
    def __init__(self):
        with open('../Utils/zaapList.json', 'r') as f:
            self.zaaps = json.load(f)
        with open('../Utils/CaracLevel.json', 'r') as f:
            self.goal_caracs = json.load(f)
        with open('../Utils/MapInfo.json', 'r') as f:
            self.full_map_info = json.load(f)
        with open('../Utils/BrakMaps.json', 'r') as f:
            self.brak_maps = json.load(f)
        with open('../Utils/BrakNorth.json', 'r') as f:
            self.brak_north_maps = json.load(f)
        with open('../Utils/BrakEast.json', 'r') as f:
            self.brak_east_maps = json.load(f)
        with open('../Utils/BworkMaps.json', 'r') as f:
            self.bwork_maps = json.load(f)
        with open('../Utils/CastleAmakna.json', 'r') as f:
            self.castle_maps = json.load(f)
        with open('../Utils/Items.json', 'r') as f:
            self.full_item_names = json.load(f)
        with open('../Utils/TresureHuntClues.json', 'r') as f:
            self.clues = json.load(f, encoding='latin-1')
        with open('../Utils/TresureHuntNoClues.json', 'r') as f:
            self.no_clues = json.load(f)
        with open('../Utils/Schedules/default.json', 'r') as f:
            self.default_schedule = json.load(f)
        with open('../Utils/resourcesIDs.json', 'r') as f:
            self.resources_ids = json.load(f)
        with open('../Utils/resourcesLevels.json', 'r') as f:
            self.resources_levels = json.load(f)
        with open('../Utils/ItemsToSell.json', 'r') as f:
            self.items_to_sell = json.load(f)
        with open('../Utils/Preferred_stuff.json', 'r') as f:
            self.preferred_stuffs = json.load(f)
        with open('../Utils/hdv_pos.json', 'r') as f:
            self.hdv_pos = json.load(f)
        with open('../Utils/DDTerr.json', 'r') as f:
            self.dd_territory_maps = json.load(f)
        with open('../Utils/WestDDTerr.json', 'r') as f:
            self.west_dd_territory_maps = json.load(f)
        with open('../Utils/NamedRecipes.json', 'r', encoding='utf8') as f:
            self.recipes = json.load(f)
        with open('../Utils/Hdv2Id.json', 'r') as f:
            self.hdv2id = json.load(f)
        with open('../Utils/Id2Hdv.json', 'r') as f:
            self.id2hdv = json.load(f)
        with open('../Utils/Id2Names.json', 'r', encoding='utf8') as f:
            self.id2names = json.load(f)
        with open('../Utils/Id2Type.json', 'r', encoding='latin-1') as f:
            self.id2type = json.load(f)
        with open('../Utils/EffectId2Name.json', 'r', encoding='latin-1') as f:
            self.effect_id2name = json.load(f)
        with open('../Utils/Equipements.json', 'r', encoding='utf8') as f:
            self.equipments = json.load(f)
        with open('../Utils/RunesStats.json', 'r', encoding='utf8') as f:
            self.runes_stats = json.load(f)
        with open('../Utils/RuneIds.json', 'r', encoding='utf8') as f:
            self.runes_ids = json.load(f)
        with open('../Utils/Fmable_stats.json', 'r', encoding='utf8') as f:
            self.fmable_stats = json.load(f)
