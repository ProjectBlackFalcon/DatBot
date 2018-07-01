from typing import List
import time
import json


class Hunt:
    def __init__(self, level, start_pos):
        self.level = level
        self.start_pos = start_pos
        self.steps = []  # type: List[Step]
        self.error = False
        self.reason = ''
        self.start_time = time.time()
        self.success = False

    def add_new_step(self, n_clues):
        self.steps.append(Step(n_clues))

    def current_step(self):
        return self.steps[-1]

    def current_clue(self):
        return self.current_step().clues[-1]

    def add_to_no_clue_list(self, clue_name, pos):
        with open('../Utils/TresureHuntNoClues.json', 'r') as f:
            no_clues = json.load(f)
        no_clues[clue_name].append([pos[0], pos[1]])
        with open('../Utils/TresureHuntNoClues.json', 'w') as f:
            json.dump(no_clues, f)

    def add_to_clue_list(self, clue_name, pos):
        with open('../Utils/TresureHuntClues.json', 'r') as f:
            clues = json.load(f)
        clues[clue_name].append([pos[0], pos[1]])
        with open('../Utils/TresureHuntClues.json', 'w') as f:
            json.dump(clues, f)
        with open('../Utils/TresureHuntCluesAutoAdd.txt', 'a') as f:
            f.write('{} | {}'.format(clue_name, pos))

    def get_no_clue_list(self, clue_name):
        with open('../Utils/TresureHuntNoClues.json', 'r') as f:
            no_clues = json.load(f)[clue_name]
        return no_clues

    def __str__(self):
        return '################## NEW HUNT ##################\n' \
               + 'Success : {}\n'.format(self.success) \
               + 'Duration : {} minutes'.format(round((time.time()-self.start_time)/60, 1))\
               + ''.join([str(step) for step in self.steps])


class Step:
    def __init__(self, n_clues):
        self.n_clues = n_clues
        self.clues = []  # type: List[Clue]

    def add_new_clue(self, name, start_pos, direction):
        self.clues.append(Clue(name, start_pos, direction))

    def validate(self, clues_left):
        for i in range(self.n_clues-clues_left):
            self.clues[i].valid = True

    def __str__(self):
        header = '\n\n########### NEW STEP ###########\n{} Clues in this step \n'.format(self.n_clues)
        clues = ''.join(['{}/{} | {}'.format(str(i+1), self.n_clues, str(self.clues[i])) for i in range(len(self.clues))])
        return header + clues


class Clue:
    def __init__(self, name, start_pos, direction):
        self.name = name
        self.start_pos = start_pos
        self.direction = direction
        self.guessed_pos = None
        self.valid = False

    def __str__(self):
        return '{:30} | Start : {} | Direction : {} | Guessed pos : {} | Valid : {}\n'.format(self.name, self.start_pos, self.direction, self.guessed_pos, self.valid)
