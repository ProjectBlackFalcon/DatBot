from selenium import webdriver
import time
import mysql.connector
import Database_credentials as dc


class Scraper:
    def __init__(self, headless=True):
        self. servers = {
            'julith': 208
        }
        print('Initialisation...')
        start = time.time()
        options = webdriver.ChromeOptions()
        if headless:
            options.add_argument('headless')
        self.driver = webdriver.Chrome(chrome_options=options)
        print('Scraper initialized in ' + str(round(time.time() - start, 1)) + ' seconds\n')

    def destroy(self):
        self.driver.quit()

    def get_link_to_profile(self, name, server):
        server = self.servers[server.lower()]
        self.driver.get("https://www.dofus.com/fr/mmorpg/communaute/annuaires/pages-persos?TEXT={}&character_homeserv%5B%5D={}&character_level_min=20&character_level_max=200#jt_list".format(name, server))
        ids = self.driver.find_elements_by_xpath("//a[@href]")
        for ii in ids:
            if 'https://www.dofus.com/fr/mmorpg/communaute/annuaires/pages-persos/' in ii.get_attribute('href'):
                return ii.get_attribute('href')

    def get_image_link(self, name, server):
        link = self.get_link_to_profile(name, server)
        if link is not None:
            self.driver.get(link + '/caracteristiques')
            ids = self.driver.find_elements_by_class_name('ak-entitylook')
            if len(ids):
                return ids[-1].get_attribute('style').split('"')[1]


if __name__ == '__main__':
    while 1:
        s = Scraper()

        conn = mysql.connector.connect(host=dc.host, user=dc.user, password=dc.password, database=dc.database)
        cursor = conn.cursor()
        cursor.execute("""SELECT name, server, banned FROM BotAccounts""")
        links = []
        for row in cursor:
            if not row[2]:
                link = s.get_image_link(row[0], row[1])
                if link is not None:
                    links.append((row[0], link))

        for link in links:
            cursor.execute("""UPDATE BotAccounts SET characterpage='{}' WHERE name='{}'""".format(link[1].replace('270_361', '600_880'), link[0]))

        conn.commit()
        conn.close()
        s.destroy()

        time.sleep(3600)
