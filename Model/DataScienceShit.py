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
from sqlalchemy import create_engine
import json


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
                SELECT min(TimeIn), max(TimeIn), TIMESTAMPDIFF(HOUR, min(TimeIn), max(TimeIn)), ItemId, Stats, price1, Hash
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
        output = pd.DataFrame(rows, columns=['TimeIn', 'TimeOut', 'TimeForSale', 'ItemId', 'Stats', 'Price', 'Hash'])
        output['Name'] = output['ItemId'].apply(lambda itemid: self.resources.id2names[str(itemid)])
        output.set_index('ItemId', drop=True, inplace=True)
        return output


class DS:
    def __init__(self, resources: Resources):
        self.resources = resources
        self.database = Database(resources)

    def estimate_craft_cost(self, server, item_id_list, rich_return=False):
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

    def item_breaking_analysis(self, server, item_id_list):
        item_id_list = [item_id_list] if type(item_id_list) is not list else item_id_list
        runes_prices = ds.database.runes_prices(server)
        craft_costs = ds.estimate_craft_cost('Julith', item_id_list, rich_return=True)
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
                report.append([item.item_name, int(item_id), int(item.level), int(craft_costs.loc[item_id]['Price']), int(hdv_value[item_id]), int(min_coeff), focus])
            except:
                non_craftable += 1
                print(self.resources.id2names[str(item_id)])
        report = pd.DataFrame(report, columns=['Item Name', 'ItemId', 'Level', 'Craft Price', 'Hdv price', 'Min coeff', 'Focus']).set_index('Item Name').sort_values('Min coeff')
        print(non_craftable)
        return report

    def item_ids_from_level(self, lvl_min, lvl_max):
        item_ids = []
        for item_id, values in self.resources.equipments.items():
            if lvl_min <= values['Level'] <= lvl_max:
                item_ids.append(int(item_id))
        return item_ids

    def items_sold_last_period(self, server, hours, item_id_list=None, lvl_min=0, lvl_max=200):
        min_date = datetime.datetime.fromtimestamp(time.time() - hours * 3600)
        ids = self.item_ids_from_level(lvl_min, lvl_max) if item_id_list is None else item_id_list
        return self.database.sold_items_from_id(server, ids, min_date)

    def estimate_item_cost(self, server, item_id_list, item=None):
        # TODO
        items = self.database.items_from_id(server, item_id_list)[['Price', 'Stats']]
        items.Stats = items.Stats.apply(lambda row: {stat[0]: stat[1] for stat in ast.literal_eval(row) if len(stat) == 2 and stat[0] not in (1151, 1152)})
        prices = items.Price.reset_index(drop=True)
        items = pd.DataFrame(items.Stats.values.tolist()).fillna(0)
        items['Price'] = pd.Series(prices)
        items = items.set_index('Price')

        scaler = StandardScaler()
        items[items.columns] = scaler.fit_transform(items)

        model = RandomForestRegressor(n_estimators=30, min_samples_leaf=1, max_depth=4)
        model.fit(items.reset_index(drop=True), items.index)
        cvres = cross_val_score(model, items.reset_index(drop=True), items.index, cv=5, scoring='neg_mean_squared_error')
        cvres = np.sqrt(-cvres)
        print(cvres, cvres.mean(), cvres.std())

    def items_turnover(self, server, period_in_hours, premade_data=None, item_id_list=None, lvl_min=0, lvl_max=200):
        if premade_data is None:
            ids = self.item_ids_from_level(lvl_min, lvl_max) if item_id_list is None else item_id_list
            values = self.items_sold_last_period(server, period_in_hours, ids).groupby('ItemId').count().Price.sort_values(ascending=False) / (period_in_hours / 24)
        else:
            values = premade_data.groupby('ItemId').count().Price.sort_values(ascending=False) / (period_in_hours / 24)
        values = pd.DataFrame(values)
        values.columns = ['Turnover']
        values['Name'] = pd.Series({item_id: self.resources.id2names[str(item_id)] for item_id in list(values.index)})
        return values

    def generate_item_datamart(self, server):
        data = pd.DataFrame(self.item_ids_from_level(1, 200), columns=['ItemId']).set_index(['ItemId'])
        data['Name'] = pd.Series({item_id: self.resources.id2names[str(item_id)] for item_id in list(data.index)})
        sold_last_week = self.items_sold_last_period(server, 24*8)

        dates = [pd.datetime.fromtimestamp(time.time() - i * 3600 * 24).date() for i in range(8)]
        data['Count'] = sold_last_week.loc[(sold_last_week['TimeOut'] < dates[0]) & (sold_last_week['TimeOut'] > dates[-1])].groupby('ItemId').Hash.count()
        data['Count'].fillna(0, inplace=True)
        data['Turnover'] = data['Count'] / (dates[0].toordinal() - dates[-1].toordinal())
        data['Turnover'].fillna(0, inplace=True)

        for i in range(len(dates)-1):
            data['SoldJ-{}'.format(i+1)] = pd.Series(sold_last_week.loc[(sold_last_week['TimeOut'] < dates[i]) & (sold_last_week['TimeOut'] > dates[i + 1])].groupby('ItemId')[['Hash', 'Price', 'Stats']].apply(lambda item: json.dumps({item_hash: [price, stats] for _, (item_hash, price, stats) in item.iterrows()})))
            data['SoldJ-{}'.format(i + 1)].fillna('{}', inplace=True)

        conn = create_engine('mysql+mysqlconnector://{}:{}@{}/{}'.format(dc.user, dc.password, dc.host, dc.database), echo=False)
        print(data.to_sql('{}Sales'.format(server), conn, if_exists='replace'))
        # return sold_last_week


if __name__ == '__main__':
    ds = DS(Resources())
    start = time.time()
    ids = ds.item_ids_from_level(0, 200)
    print(ds.item_breaking_analysis('Julith', ids).to_csv('out.csv'))
    print(int(time.time() - start))

# TODO items sold last day/week
# TODO mean time for sale
# TODO Générateur d'astuce kamas
