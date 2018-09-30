import pandas as pd
from Resources import Resources
import Database_credentials as dc
import mysql.connector
from Item import Item


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
        return output


class DS:
    def __init__(self, resources: Resources):
        self.resources = resources
        self.database = Database(resources)

    def estimate_craft_cost(self, item_id_list, server):
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
        ingredients = recipes['Ingredients'].apply(lambda ingredients: pd.DataFrame(ingredients, columns=['ItemId', 'Quantity']).set_index('ItemId', drop=True).sort_index())
        prices = ingredients.apply(lambda recipe: self.database.resource_price_from_db(server, recipe.index.tolist())['PriceAvg'])

        quantities = pd.DataFrame(ingredients.apply(lambda item: item['Quantity'])).T
        price_per_ingredient = prices.T.mul(quantities)

        for i in range(len(ingredients)):
            ingredients.iloc[i]['Price'] = price_per_ingredient.iloc[:, i].dropna()
        recipes['Price'] = price_per_ingredient.sum()
        return recipes


if __name__ == '__main__':
    ds = DS(Resources())
    print(ds.estimate_craft_cost([958, 2629, 6805, 8287], 'Julith'))