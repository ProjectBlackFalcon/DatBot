import mysql.connector
from tkinter import *
import datetime


class Bot:
    def __init__(self):
        self.id = None
        self.credentials = None
        self.occupation = None
        self.position = None
        self.connected = False
        self.in_fight = False
        self.subscribed = None
        self.kamas = None
        self.level = None
        self.last_update = None

    def to_string(self):
        return '{:^25} {:^7} {:^15} {:^50} {:^20}'.format(self.credentials['name'], self.level, self.kamas, self.occupation, self.last_update)


class AccountManager():
    def __init__(self):
        self.bots = []
        self.tk = Tk()
        self.tk.title('Bot Manager')
        self.frame = Frame(self.tk, height=500, width=500)
        self.frame.pack()

        self.build_ui()

        self.tk.mainloop()

    def build_ui(self):
        self.add_bot_btn = Button(self.frame, text='Add bot', command=self.add_bot, font='courier')
        self.add_bot_btn.pack()
        self.add_bot_frame = Frame(self.frame)
        self.add_bot_frame.pack()

        self.get_bots_db()
        self.bots_frame = Frame(self.frame, bg='#666666')
        self.bots_frame.pack()
        self.bot_labels = []
        self.bot_labels.append(Label(self.bots_frame, font='courier', text='{:^25} {:^7} {:^15} {:^50} {:^20}'.format('Name', 'Level', 'Kamas', 'Occupation', 'Last update')))
        self.bot_labels[-1].pack()
        for bot in self.bots:
            botstring = bot.to_string()
            self.bot_labels.append(Label(self.bots_frame, text=botstring, font='courier'))
            self.bot_labels[-1].pack()

    def add_bot(self):
        def ok():
            inputs = []
            for entry in entries:
                inp = entry.get()
                if inp:
                    inputs.append(inp)
            self.add_bot_db(inputs[0], inputs[1], inputs[2], inputs[3])
            self.add_bot_frame_labels.destroy()
            self.add_bot_frame_entries.destroy()
            self.add_bot_frame_btns.destroy()

        def cancel():
            self.add_bot_frame_labels.destroy()
            self.add_bot_frame_entries.destroy()
            self.add_bot_frame_btns.destroy()

        self.add_bot_frame_labels = Frame(self.add_bot_frame)
        self.add_bot_frame_labels.pack()
        fields = ['{:^15}'.format('Username'), '{:^15}'.format('Password'), '{:^15}'.format('Name'), '{:^15}'.format('Server')]
        for text in fields:
            Label(self.add_bot_frame_labels, text=text, font='courier').pack(side='left')

        self.add_bot_frame_entries = Frame(self.add_bot_frame)
        self.add_bot_frame_entries.pack()
        entries = []
        for text in fields:
            entries.append(Entry(self.add_bot_frame_entries, width=15, font='courier'))
            entries[-1].pack(side='left')

        self.add_bot_frame_btns = Frame(self.add_bot_frame)
        self.add_bot_frame_btns.pack()
        self.add_bot_ok_btn = Button(self.add_bot_frame_btns, text='OK', font='courier', width=10, command=ok)
        self.add_bot_ok_btn.pack(side='left')
        self.add_bot_cancel_btn = Button(self.add_bot_frame_btns, text='Cancel', font='courier', width=10, command=cancel)
        self.add_bot_cancel_btn.pack(side='left')

    def get_bots_db(self):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""SELECT * FROM BotAccounts""")
        for bot in cursor:
            new_bot = Bot()
            credentials = {
                'username': bot[1],
                'password': bot[2],
                'name': bot[3],
                'server': bot[4]
            }
            new_bot.credentials = credentials
            self.bots.append(new_bot)

        for bot in self.bots:
            cursor.execute("""SELECT * FROM (SELECT * FROM Bots WHERE Name = %s) t2 WHERE Time IN (SELECT max(Time) FROM (SELECT * FROM Bots WHERE Name = %s) t3)""", (bot.credentials['name'], bot.credentials['name']))
            for data in cursor:
                bot.kamas = data[3]
                bot.level = data[4]
                bot.occupation = data[5]
                bot.position = (data[6], data[7])
                bot.last_update = str(data[8])
        conn.close()

    def add_bot_db(self, username, password, name, server):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        put = (username, password, name, server)
        cursor.execute("""SELECT * FROM BotAccounts WHERE username = %s""", (username,))
        things = []
        for thing in cursor:
            things.append(thing)
        if not things:
            cursor.execute("""INSERT INTO BotAccounts (username, password, name, server) VALUES (%s, %s, %s, %s)""",
                           put)
            conn.commit()
        conn.close()

    def del_bot_db(self, username):
        conn = mysql.connector.connect(host="154.49.211.32", user="wz3xj6_spec", password="specspec",
                                       database="wz3xj6_spec")
        cursor = conn.cursor()
        cursor.execute("""DELETE FROM BotAccounts WHERE username = %s""", (username,))
        conn.commit()
        conn.close()

AM = AccountManager()