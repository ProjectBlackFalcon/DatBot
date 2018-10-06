import pandas as pd
import numpy as np
from Resources import Resources
import Database_credentials as dc
import mysql.connector
from Item import Item
import time
import datetime
import ast
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import cross_val_score
from sklearn.ensemble import RandomForestRegressor


class Database:
    def __init__(self, resources: Resources):
        self.resources = resources

    def resources_from_id(self, server, item_id_list):
        item_id_list = [item_id_list] if type(item_id_list) is int else item_id_list
        item_id_list = tuple(item_id_list) if len(item_id_list) > 1 else item_id_list
        item_id_list = 'NULL' if type(item_id_list) is not tuple and not len(item_id_list) else item_id_list
        item_id_list = item_id_list[0] if len(item_id_list) == 1 else item_id_list
        operator = 'IN' if type(item_id_list) is tuple else '='

        conn = mysql.connector.connect(host=dc.host, user=dc.user, password=dc.password, database=dc.database)
        cursor = conn.cursor()
        cursor.execute("""  
            SELECT Time, ItemId, Price1, Price10, Price100, Priceavg
            FROM ResourcePrices
            WHERE Time IN (
                SELECT MAX(Time)
                FROM ResourcePrices
                WHERE ItemId {} {} AND Server = '{}'
                GROUP BY ItemId
            ) AND ItemId {} {}
        """.format(operator, item_id_list, server, operator, item_id_list))

        rows = cursor.fetchall()
        output = pd.DataFrame(rows, columns=['Time', 'ItemId', 'Price1', 'Price10', 'Price100', 'PriceAvg'])
        output['Name'] = output['ItemId'].apply(lambda itemid: self.resources.id2names[str(itemid)])
        output.set_index('ItemId', drop=True, inplace=True)
        output = output.loc[~output.index.duplicated(keep='first')]
        return output

    def items_from_id(self, server, item_id_list):
        item_id_list = [item_id_list] if type(item_id_list) is int else item_id_list
        item_id_list = tuple(item_id_list) if len(item_id_list) > 1 else item_id_list
        item_id_list = 'NULL' if type(item_id_list) is not tuple and not len(item_id_list) else item_id_list
        item_id_list = item_id_list[0] if len(item_id_list) == 1 else item_id_list
        operator = 'IN' if type(item_id_list) is tuple else '='

        conn = mysql.connector.connect(host=dc.host, user=dc.user, password=dc.password, database=dc.database)
        cursor = conn.cursor()
        cursor.execute("""  
                SELECT TimeIn, ItemId, Price1, Stats, Hash
                FROM ItemPrices
                WHERE Server = '{}' AND ItemId {} {} AND TimeIn IN (
                    SELECT MAX(TimeIn)
                    FROM ItemPrices
                    GROUP BY ItemId
                )""".format(server, operator, item_id_list))
        rows = cursor.fetchall()
        output = pd.DataFrame(rows, columns=['Time', 'ItemId', 'Price', 'Stats', 'Hash'])
        output['Name'] = output['ItemId'].apply(lambda itemid: self.resources.id2names[str(itemid)])
        output.set_index('ItemId', drop=True, inplace=True)
        return output

    def runes_prices(self, server):
        return self.resources_from_id(server, self.resources.runes_ids.values())

    def sold_items_from_id(self, server, item_id_list, min_date=datetime.datetime.fromtimestamp(0)):
        item_id_list = [item_id_list] if type(item_id_list) is int else item_id_list
        item_id_list = tuple(item_id_list) if len(item_id_list) > 1 else item_id_list
        item_id_list = 'NULL' if type(item_id_list) is not tuple and not len(item_id_list) else item_id_list
        item_id_list = item_id_list[0] if len(item_id_list) == 1 else item_id_list
        operator = 'IN' if type(item_id_list) is tuple else '='

        conn = mysql.connector.connect(host=dc.host, user=dc.user, password=dc.password, database=dc.database)
        cursor = conn.cursor()
        cursor.execute("""
                SELECT min(TimeIn), max(TimeIn), TIMESTAMPDIFF(HOUR, min(TimeIn), max(TimeIn)), ItemId, Stats, price1
                FROM ItemPrices
                WHERE ItemId {operator} {ids} AND Server = '{server}' AND TimeIn > '{min_date}' AND Hash NOT IN (
                    SELECT Hash
                    FROM ItemPrices
                    WHERE ItemId {operator} {ids} AND Server = '{server}' AND TimeIn IN (
                        SELECT max(TimeIn)
                        FROM ItemPrices
                        WHERE ItemId {operator} {ids} AND Server = '{server}'
                        GROUP BY ItemId
                    )
                )
                GROUP BY Hash
                ORDER BY ItemId""".format(operator=operator, ids=item_id_list, server=server, min_date=min_date))
        rows = cursor.fetchall()
        output = pd.DataFrame(rows, columns=['TimeIn', 'TimeOut', 'TimeForSale', 'ItemId', 'Stats', 'Price'])
        output['Name'] = output['ItemId'].apply(lambda itemid: self.resources.id2names[str(itemid)])
        output.set_index('ItemId', drop=True, inplace=True)
        return output


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
        prices = self.database.resources_from_id(server, ingredients.keys())['PriceAvg']
        items_in_recipe_with_prices = [item_id for item_id in ingredients.keys() if str(item_id) in self.resources.equipments]
        items = self.database.items_from_id(server, items_in_recipe_with_prices).drop(columns=['Time', 'Name', 'Stats', 'Hash'])
        items_in_recipe_with_prices = items.groupby('ItemId')['Price'].min()
        prices = prices.append(pd.Series(items_in_recipe_with_prices))

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
        runes_prices = ds.database.runes_prices(server)
        craft_costs = ds.estimate_craft_cost(item_id_list, 'Julith', rich_return=True)
        items = self.database.items_from_id(server, item_id_list).drop(columns=['Time', 'Name', 'Stats', 'Hash'])
        items_in_recipe_with_prices = items.groupby('ItemId')['Price'].min()
        hdv_value = dict(items_in_recipe_with_prices)
        report = []
        non_craftable = 0
        for item_id in item_id_list:
            try:
                item = Item(self.resources, item_id=item_id, creation_price=craft_costs.loc[item_id]['Price'])
                item.generate_random_instance()
                min_coeff, focus = item.get_min_coeff(runes_prices)
                report.append([item.item_name, int(item.level), int(craft_costs.loc[item_id]['Price']), int(hdv_value[item_id]), int(min_coeff), focus])
            except:
                non_craftable += 1
                print(self.resources.id2names[str(item_id)])
        report = pd.DataFrame(report, columns=['Item Name', 'Level', 'Craft Price', 'Hdv price', 'Min coeff', 'Focus']).set_index('Item Name')
        print(non_craftable)
        return report

    def item_ids_from_level(self, lvl_min, lvl_max):
        item_ids = []
        for item_id, values in self.resources.equipments.items():
            if lvl_min <= values['Level'] <= lvl_max:
                item_ids.append(int(item_id))
        return item_ids

    def items_sold_last_period(self, server, hours, lvl_min=0, lvl_max=200):
        min_date = datetime.datetime.fromtimestamp(time.time() - hours * 3600)
        ids = self.item_ids_from_level(lvl_min, lvl_max)
        return self.database.sold_items_from_id(server, ids, min_date)

    def estimate_item_cost(self, server, item_id_list, item=None):
        items = self.database.items_from_id(server, item_id_list)[['Price', 'Stats']]
        items.Stats = items.Stats.apply(lambda row: {stat[0]: stat[1] for stat in ast.literal_eval(row) if len(stat) == 2 and stat[0] not in (1151, 1152)})
        prices = items.Price.reset_index(drop=True)
        items = pd.DataFrame(items.Stats.values.tolist()).fillna(0)
        items['Price'] = pd.Series(prices)
        items = items.set_index('Price')

        scaler = StandardScaler()
        items[items.columns] = scaler.fit_transform(items)

        model = RandomForestRegressor(n_estimators=30, min_samples_leaf=1, max_depth=4)
        # model.fit(items.reset_index(drop=True), items.index)
        cvres = cross_val_score(model, items.reset_index(drop=True), items.index, cv=5, scoring='neg_mean_squared_error')
        cvres = np.sqrt(-cvres)
        print(cvres, cvres.mean(), cvres.std())


if __name__ == '__main__':
    ds = DS(Resources())
    start = time.time()
    ds.estimate_item_cost('Julith', 8876)
    print(time.time() - start)

# TODO items sold last day/week
# TODO mean time for sale
# TODO Générateur d'astuce kamas
