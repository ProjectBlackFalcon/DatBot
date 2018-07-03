import mysql.connector
import Database_credentials as dc

with open('Utils/BotsLogs/Maxitreur.txt', 'r') as f:
    master_log = ''.join(f.readlines()).replace("'", '"')
with open('Utils/BotsLogs/Maxitreur.txt', 'w') as f:
    f.write('')

with open('log_network0.txt', 'r') as f:
    net_log = ''.join(f.readlines()).replace("'", '"')

conn = mysql.connector.connect(host=dc.host, user=dc.user, password=dc.password, database=dc.database)
cursor = conn.cursor()
cursor.execute("""INSERT INTO ManualLogs (master, network) VALUES ('{}', '{}')""".format(master_log, net_log))
conn.commit()
conn.close()