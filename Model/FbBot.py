import fbchat
import fbCreds
from fbchat.models import *
import time
from LowLevelFunctions import LowLevelFunctions
import sys


class FbBot:
    def __init__(self, llf: LowLevelFunctions):
        self.client = fbchat.Client(fbCreds.username, fbCreds.password)
        self.conv_id = fbCreds.bot_conv
        self.group_members = self.client.fetchGroupInfo(self.conv_id)[str(self.conv_id)].nicknames
        self.llf = llf

    def send_alerts(self):
        alerts = self.llf.fetch_alerts()
        for author, item_id, min_price, max_price in alerts:
            quants, avg = self.llf.resource_is_for_sale(item_id)
            if quants != [-1, -1, -1]:
                max_price = sys.maxsize if max_price == -1 else max_price
                quantities = [10**i for i in range(len(quants)) if min_price <= quants[i]/10**i <= max_price and quants[i] != -1]
                prices = [price for price in quants if min_price <= price/10**quants.index(price) <= max_price and price != -1]
                message = 'Alert for {author} : item "{itemname}" is available in {quantities} for {prices}. Usual average is {average}'.format(author=author, itemname=self.llf.resources.id2names[str(item_id)], quantities=quantities, prices=prices, average=avg)
                self.send_message(message)

    def send_message(self, message):
        self.client.send(Message(text=message), self.conv_id, thread_type=ThreadType.GROUP)

    def fb_bot(self):
        while 1:
            messages = self.client.fetchThreadMessages(thread_id=self.conv_id, limit=1)
            if 'request' == messages[0].text.lower().split(' ')[0].strip():
                if 'alive' == messages[0].text.lower().split(' ')[1].strip():
                    pass
                else:
                    self.client.send(Message(text='Va chier {}'.format(self.group_members[messages[0].author])), self.conv_id, thread_type=ThreadType.GROUP)
            time.sleep(1)