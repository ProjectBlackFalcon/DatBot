from subprocess import Popen, PIPE
import sys

args = ['java', '-jar', '..//Main.jar']
ON_POSIX = 'posix' in sys.builtin_module_names

p = Popen(args, stdin=PIPE, stdout=PIPE, bufsize=10, close_fds=ON_POSIX)
#STARTING GAME WITH MAP ID 84675595
p.stdin.write("0;403;f;cmd;startfight;[84675595]\r\n")

#Init entity 0 in cell[10,12], it's a player with ID 0 (Cra), in team 1.
#Stats : 2020HP, 11PA, 4PM (max)
entity0 = "[0,10,12,'p',0,1,2020,11,4]"

#Init entity 1 in cell[6,12], it's a monster with ID 0 (Cra atm, will change), in team 0.
#Stats : 200HP, 15PA, 5PM (max)
entity1 = "[1,6,12,'m',0,0,200,15,5]"

p.stdin.write("0;404;f;cmd;s;["+entity0+"]:["+entity1+"]\r\n")

#Moving entity 0 to cell[11,12]
p.stdin.write("0;405;f;cmd;m;0;11;12\r\n")

#Entity 0 passing turn
p.stdin.write("0;406;f;cmd;p;0\r\n")

#Moving entity 1 to cell [12, 12]
p.stdin.write("0;407;f;cmd;m;1;12;12\r\n")

#Entity 1 passing turn
p.stdin.write("0;406;f;cmd;p;1;\r\n")

#Entity 1 casting magic arrow to cell[9,12]. 150 damage, not a crit.
p.stdin.write("0;408;f;cmd;c;0;9;12;'Magic arrow';150;False\r\n")

#Entity 0 passing turn
p.stdin.write("0;409;f;cmd;p;0\r\n")

#Getturn request for entity 1
p.stdin.write("0;410;f;cmd;g;1\r\n")

p.stdin.write("x\r\n") # this line will not be printed into the file

print "finished"