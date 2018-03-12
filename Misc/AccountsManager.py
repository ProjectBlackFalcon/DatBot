import mysql.connector
from tkinter import filedialog
from tkinter import *
import datetime
import json
import copy


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
        return '{:^25} {:^7} {:^15} {:^50} {:^20}'.format(self.credentials['name'], self.level, self.kamas,
                                                          self.occupation, self.last_update)


class ScheduleBar:
    def __init__(self, duration, idx, tasks=None):
        self.duration = duration
        self.tasks = [] if tasks is None else tasks
        self.idx = idx


class Scheduler:
    def __init__(self):
        self.tasks = {
            'sleep': '#aaaaaa',
            'hunt': '#cc5555',
            'dd': '#55cc55',
            'sell': '#5555cc'
        }
        self.tk = Tk()
        self.tk.title('Schedule creator')
        self.frame = Frame(self.tk, height=500, width=500)
        self.frame.pack()
        Label(self.frame, text='New bar : new <duration>').pack()
        Label(self.frame, text='Del bar : del <bar id/all> | clear').pack()
        Label(self.frame, text='Add task : <task> <bar id> <start> <end>').pack()
        Label(self.frame, text='Resize bar : resize <bar id> <new duration>').pack()
        Label(self.frame, text='Add bar1 to bar 2 : add <bar1 id> <bar2 id> <offset>').pack()
        Label(self.frame, text='Save : save <file name>').pack()
        Label(self.frame, text='Load : load <file name (optionnal)>').pack()
        Label(self.frame, text='TASKS : ').pack()
        Label(self.frame, text=str(list(self.tasks.keys()))).pack()
        self.command_field = Entry(self.frame, width=40)
        self.command_field.pack(anchor='center')
        self.command_field.bind("<Return>", self.eval)
        self.last_command = ''
        self.command_field.bind("<Up>", self.redo)
        self.bars = []
        self.bar_canvas = Canvas(self.frame, width=230, height=200)
        self.bar_canvas.pack()

    def eval(self, event=None):
        command = self.command_field.get()
        self.last_command = command
        self.command_field.delete(0, 'end')
        print(command)

        if 'new' in command:
            self.add_bar(int(command.split(' ')[1]))

        elif 'del' in command:
            self.del_bar(command.split(' ')[1])

        elif 'clear' in command:
            self.del_bar('all')

        elif command.split(' ')[0] in self.tasks.keys():
            task, idx, start, end = command.split(' ')
            idx, start, end = int(idx), int(start), int(end)
            selected_bar = None
            for bar in self.bars:
                if bar.idx == idx:
                    selected_bar = bar
            selected_bar.tasks.append({'name': task, 'start': start, 'end': end})
            print(selected_bar.idx, selected_bar.tasks)
            self.draw_bars()

        elif 'save' in command:
            _, file_name = command.split(' ')
            self.save(file_name)

        elif 'load' in command:
            split_cmd = command.split(' ')
            if len(split_cmd) > 1:
                self.load(split_cmd[1])
            else:
                self.load()

        elif 'resize' in command:
            _, idx, new_duration = command.split(' ')
            idx, new_duration = int(idx), int(new_duration)
            selected_bar = None
            for bar in self.bars:
                if bar.idx == idx:
                    selected_bar = bar
            self.resize_bar(selected_bar, new_duration)

        elif 'add' in command:
            _, idx1, idx2, offset = command.split(' ')
            idx1, idx2, offset = int(idx1), int(idx2), int(offset)
            selected_bar1, selected_bar2 = None, None
            for bar in self.bars:
                if bar.idx == idx1:
                    selected_bar1 = bar
                elif bar.idx == idx2:
                    selected_bar2 = bar
            self.concat_bars(selected_bar1, selected_bar2, offset)

    def redo(self, _):
        self.command_field.insert(0, self.last_command)

    def concat_bars(self, bar1, bar2, offset):
        # puts bar1 in bar2 at value
        print(bar1.idx, bar1.tasks)
        tasks = copy.deepcopy(bar1.tasks)
        for task in tasks:
            task['start'] += offset
            task['end'] += offset
        bar2.tasks += tasks
        self.draw_bars()

    def resize_bar(self, bar, new_duration):
        for task in bar.tasks:
            task['start'] = task['start'] * new_duration / bar.duration
            task['end'] = task['end'] * new_duration / bar.duration
        bar.duration = new_duration
        self.draw_bars()

    def save(self, file_name):
        bars = []
        for bar in self.bars:
            flattened = [None]*1440
            for task in bar.tasks:
                for i in range(int(task['start']*1440/bar.duration), int(task['end']*1440/bar.duration)):
                    flattened[i] = task['name']

            tasks = [{'name': flattened[0], 'start': 0, 'end': 0}] if flattened[0] is not None else []
            for i in range(1, len(flattened)):
                if flattened[i] == flattened[i-1] and flattened[i] is not None:
                    tasks[-1]['end'] += bar.duration / 1440
                elif flattened[i] is not None:
                    tasks.append({'name': flattened[i], 'start': i*bar.duration / 1440, 'end': i*bar.duration / 1440})

            bars.append({'tasks': copy.deepcopy(tasks), 'duration': bar.duration, 'idx': bar.idx})

        with open('..//Utils//Schedules//{}.json'.format(file_name), 'w') as f:
            json.dump(bars, f)

    def load(self, file_name=None):
        if file_name is None:
            path = False
            path = filedialog.askopenfilename(initialdir="..//Utils//Schedules//", title="Select schedule",
                                              filetypes=(("json files", "*.json"), ("all files", "*.*")))
        else:
            path = "..//Utils//Schedules//{}.json".format(file_name)

        bars = []
        if path:
            with open(path, 'r') as f:
                bars = json.load(f)
        for bar in bars:
            self.bars.append(ScheduleBar(bar['duration'], bar['idx']+len(self.bars), bar['tasks']))
        self.draw_bars()

    def add_bar(self, duration):
        self.bars.append(ScheduleBar(duration, len(self.bars)))
        self.draw_bars()

    def del_bar(self, idx):
        if idx == 'all':
            [self.del_bar(0) for i in range(len(self.bars))]
        else:
            idx = int(idx)
            del self.bars[idx]
            for i in range(len(self.bars)):
                self.bars[i].idx = i
            self.draw_bars()

    def draw_bars(self):
        self.bar_canvas.delete('all')
        for bar in self.bars:
            idx = bar.idx
            self.bar_canvas.create_rectangle((10, (idx+1)*20, 210, (idx+1)*20+20), fill='#555555')
            self.bar_canvas.create_text((5, (idx+1)*20+10), text=str(idx))
            self.bar_canvas.create_text((220, (idx+1)*20+10), text=bar.duration)
            for task in bar.tasks:
                startx = 10+int(task['start']*200/bar.duration)
                endx = 10+int(task['end']*200/bar.duration)
                self.bar_canvas.create_rectangle((startx, (idx + 1) * 20 + 1, endx, (idx + 1) * 20 + 20),
                                                 fill=self.tasks[task['name']], outline='')


class AccountManager:
    def __init__(self):
        self.bots = []
        self.tk = Tk()
        self.tk.title('Bot Manager')
        self.frame = Frame(self.tk, height=500, width=500)
        self.frame.pack()

        self.build_ui()

        self.tk.mainloop()

    def bot_action(self, bot):
        Scheduler()

    def build_ui(self):
        self.add_bot_btn = Button(self.frame, text='Add bot', command=self.add_bot, font='courier')
        self.add_bot_btn.pack()
        self.add_bot_frame = Frame(self.frame)
        self.add_bot_frame.pack()

        self.get_bots_db()
        self.bots_frame = Frame(self.frame)
        self.bots_frame.pack()
        self.bot_labels, self.bot_buttons = [], []
        self.bot_labels.append(Label(self.bots_frame, font='courier',
                                     text='{:^25} {:^7} {:^15} {:^50} {:^20}'.format('Name', 'Level', 'Kamas',
                                                                                     'Occupation', 'Last update')))
        self.bot_labels[-1].grid(row=0, column=0)

        self.sorted_bots = sorted(self.bots, key=lambda one_bot: one_bot.last_update, reverse=True)
        for i in range(len(self.sorted_bots)):
            bot = self.sorted_bots[i]
            botstring = bot.to_string()
            self.bot_labels.append(Label(self.bots_frame, text=botstring, font='courier'))
            self.bot_labels[-1].grid(row=i+1, column=0)
            self.bot_buttons.append(
                Button(master=self.bots_frame, text='LEL', font='courier', command=lambda idx=i: self.bot_action(idx)))
            self.bot_buttons[-1].grid(row=i+1, column=1)

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
            self.tk.wm_geometry("")

        def cancel():
            self.add_bot_frame_labels.destroy()
            self.add_bot_frame_entries.destroy()
            self.add_bot_frame_btns.destroy()

        self.add_bot_frame_labels = Frame(self.add_bot_frame)
        self.add_bot_frame_labels.pack()
        fields = ['{:^15}'.format('Username'), '{:^15}'.format('Password'), '{:^15}'.format('Name'),
                  '{:^15}'.format('Server')]
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
        self.add_bot_cancel_btn = Button(self.add_bot_frame_btns, text='Cancel', font='courier', width=10,
                                         command=cancel)
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
            cursor.execute(
                """SELECT * FROM (SELECT * FROM Bots WHERE Name = %s) t2 WHERE Time IN (SELECT max(Time) FROM (SELECT * FROM Bots WHERE Name = %s) t3)""",
                (bot.credentials['name'], bot.credentials['name']))
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
