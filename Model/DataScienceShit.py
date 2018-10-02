import pandas as pd
from Resources import Resources
import Database_credentials as dc
import mysql.connector
from Item import Item
import time


class Database:
    def __init__(self, resources: Resources):
        self.resources = resources

    def resource_price_from_db(self, server, id_list):
        conn = mysql.connector.connect(host=dc.host, user=dc.user, password=dc.password, database=dc.database)
        cursor = conn.cursor()
        cursor.execute("""  
            SELECT Time, ItemId, Price1, Price10, Price100, Priceavg
            FROM ResourcePrices
            WHERE Time IN (
                SELECT MAX(Time)
                FROM ResourcePrices
                WHERE ItemId IN {} AND Server = '{}'
                GROUP BY ItemId
            ) AND ItemId IN {}
        """.format(tuple(id_list), server, tuple(id_list)))

        rows = cursor.fetchall()
        output = pd.DataFrame(rows, columns=['Time', 'ItemId', 'Price1', 'Price10', 'Price100', 'PriceAvg'])
        output['Name'] = output['ItemId'].apply(lambda itemid: self.resources.id2names[str(itemid)])
        output.set_index('ItemId', drop=True, inplace=True)
        output = output.loc[~output.index.duplicated(keep='first')]
        return output

    def get_item_from_id(self, item_id, server):
        conn = mysql.connector.connect(host=dc.host, user=dc.user, password=dc.password, database=dc.database)
        cursor = conn.cursor()
        cursor.execute("""  
                    SELECT TimeIn, ItemId, Price1, Stats, Hash
                    FROM ItemPrices
                    WHERE TimeIn IN (
                        SELECT MAX(TimeIn)
                        FROM ItemPrices
                        WHERE ItemId = {} AND Server = '{}'
                    ) AND ItemId = {}
                """.format(item_id, server, item_id))
        rows = cursor.fetchall()
        output = pd.DataFrame(rows, columns=['Time', 'ItemId', 'Price', 'Stats', 'Hash'])
        output['Name'] = output['ItemId'].apply(lambda itemid: self.resources.id2names[str(itemid)])
        output.set_index('ItemId', drop=True, inplace=True)
        return output

    def get_runes_prices(self, server):
        return self.resource_price_from_db(server, self.resources.runes_ids.values())


class DS:
    def __init__(self, resources: Resources):
        self.resources = resources
        self.database = Database(resources)

    def estimate_craft_cost(self, item_id_list, server, rich_return=False):
        item_id_list = [item_id_list] if type(item_id_list) is not list else item_id_list
        recipes = []
        ingredients = {}
        for recipe in self.resources.recipes:
            if recipe['resultId'] in item_id_list:
                recipes.append(recipe)
                for ingredient, quantity in recipe['Ingredients']:
                    if ingredient in ingredients.keys():
                        ingredients[ingredient] = [ingredients[ingredient][0] + quantity, -1]
                    else:
                        ingredients[ingredient] = [quantity, -1]

        recipes = pd.DataFrame(recipes).set_index('resultId', drop=True)
        prices = self.database.resource_price_from_db(server, ingredients.keys())['PriceAvg']
        items_in_recipe_with_price = {}
        for item_id in ingredients.keys():
            if str(item_id) in self.resources.equipments:
                data = self.database.get_item_from_id(item_id, server).sort_values('Price')
                if len(data):
                    items_in_recipe_with_price[item_id] = data.Price.iloc[0]
        prices = prices.append(pd.Series(items_in_recipe_with_price))

        ingredients = recipes['Ingredients'].apply(lambda ingredients: pd.DataFrame(ingredients, columns=['ItemId', 'Quantity']).set_index('ItemId', drop=True).sort_index())

        quantities = pd.DataFrame(ingredients.apply(lambda item: item['Quantity'])).T
        price_per_ingredient = quantities.T.mul(prices).T

        for i in range(len(ingredients)):
            ingredients.iloc[i]['Price'] = price_per_ingredient.iloc[:, i].dropna()
        recipes['Price'] = price_per_ingredient.sum()

        if rich_return:
            return recipes
        elif len(recipes) == 1:
            return int(recipes.Price)
        else:
            return list(recipes.Price)

    def item_breaking_analysis(self, item_id_list, server):
        item_id_list = [item_id_list] if type(item_id_list) is not list else item_id_list
        runes_prices = ds.database.get_runes_prices(server)
        craft_costs = ds.estimate_craft_cost(item_id_list, 'Julith', rich_return=True)
        report = []
        non_craftable = 0
        for item_id in item_id_list:
            print('Running simulation for item {}/{}'.format(item_id_list.index(item_id) + 1, len(item_id_list)))
            try:
                item = Item(self.resources, item_id=item_id, creation_price=craft_costs.loc[item_id]['Price'])
                item.generate_random_instance()
                min_coeff, focus = item.get_min_coeff(runes_prices)
                report.append([item.item_name, int(item.level), int(craft_costs.loc[item_id]['Price']), int(min_coeff), focus])
            except:
                non_craftable += 1
                print(self.resources.id2names[str(item_id)])
        report = pd.DataFrame(report, columns=['Item Name', 'Level', 'Craft Price', 'Min coeff', 'Focus']).set_index('Item Name')
        print(non_craftable)
        return report

    def get_item_ids_from_level(self, lvl_min, lvl_max):
        item_ids = []
        for item_id, values in self.resources.equipments.items():
            if lvl_min <= values['Level'] <= lvl_max:
                item_ids.append(int(item_id))
        return item_ids


if __name__ == '__main__':
    ds = DS(Resources())
    start = time.time()
    ids = ds.get_item_ids_from_level(151, 165)
    print(ids)
    report = ds.item_breaking_analysis(ids, 'Julith').sort_values(by=['Min coeff'], ascending=False)
    report.to_csv('RuneReport.csv')
    print(time.time() - start)
