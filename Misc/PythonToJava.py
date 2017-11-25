from subprocess import Popen, PIPE
import time
from threading import Thread
import sys
from queue import Queue, Empty

ON_POSIX = 'posix' in sys.builtin_module_names


def enqueue_output(out, queue):
    for line in iter(out.readline, b''):
        queue.put(line)
    out.close()

p = Popen(['java', '-jar', '..//BotTest.jar'], stdin=PIPE, stdout=PIPE, bufsize=2, close_fds=ON_POSIX)
q = Queue()
t = Thread(target=enqueue_output, args=(p.stdout, q))
t.daemon = True  # thread dies with the program
t.start()

print('Started')
time.sleep(1)
sent = 0

while 1:
    try:
        while 1:
            read = q.get_nowait()  # or q.get(timeout=.1)
            print(read.strip().decode('latin-1'))
    except Empty:
        read = []

    if sent < 3:
        message = '{};{};i;cmd;{};{}\r\n'.format(0, 0, 'getMap', None)
        print('[Interface] Sending : ', message)
        p.stdin.write(bytes(message, 'utf-8'))
        p.stdin.flush()
        sent += 1

    time.sleep(0.5)
__author__ = 'Alexis'
