package game.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;

import main.communication.DisplayInfo;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogReplyMessage;
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.context.roleplay.GameRolePlayNpcInformations;
import protocol.network.types.game.data.items.ObjectItem;
import protocol.network.types.game.data.items.ObjectItemToSellInBid;
import utils.GameData;

public class Npc {

	private boolean dialogOver;
	private List<GameRolePlayNpcInformations> npc = new ArrayList<>();
	private Network network;
	private List<ObjectItemToSellInBid> itemsToSell;
	private List<Integer> canSell;
	private List<Long> currentPrice;

	public Npc(Network network) throws Exception {
		this.network = network;
	}

	public int getReplyId(int messageId) {
		int replyId = -1;
		switch (messageId) {
			case 318:
				replyId = 259;
				break;
			case 20954:
				replyId = 25209;
				break;
			case 20973:
				replyId = 25207;
				break;
			case 30637:
				replyId = 36982;
				break;
			case 30639:
				replyId = 36980;
				break;
		}
		return replyId;
	}

	public void reply(int id) throws Exception {
		Thread.sleep(new Random().nextInt(120) + 450);
		if (id != -1) {
			NpcDialogReplyMessage dialogReplyMessage = new NpcDialogReplyMessage(id);
			this.network.sendToServer(dialogReplyMessage, NpcDialogReplyMessage.ProtocolId, "Replying");
		}
		else {
			throw new Exception("Don't know what to respond");
		}
	}

	/**
	 * Transform the list into json string
	 * 
	 * @return String : Items selling
	 */
	public String getToSell() {
		if (itemsToSell.isEmpty()) { return "\"empty\""; }
		String str = "";
		for (int i = 0; i < itemsToSell.size(); i++) {
			if (i == itemsToSell.size() - 1) {
				str += "[" + "\"" + DisplayInfo.cleanString(GameData.getItemName(itemsToSell.get(i).getObjectGID())) + "\"" + "," + itemsToSell.get(i).getObjectGID() + "," + itemsToSell.get(i).getQuantity() + "," + itemsToSell.get(i).getObjectPrice() + "]";
			}
			else {
				str += "[" + "\"" + DisplayInfo.cleanString(GameData.getItemName(itemsToSell.get(i).getObjectGID())) + "\"" + "," + itemsToSell.get(i).getObjectGID() + "," + itemsToSell.get(i).getQuantity() + "," + itemsToSell.get(i).getObjectPrice() + "],";
			}
		}
		return str;
	}

	/**
	 * Npc can sell the item
	 * 
	 * @param i : id of the item
	 * @return boolean : can sell the item
	 */
	public boolean canSell(int i) {
		for (Integer integer : canSell) {
			System.out.println(integer);
		}
		return canSell.contains(i);
	}

	public boolean isSelling(int id) {
		for (int i = 0; i < itemsToSell.size(); i++) {
			if (itemsToSell.get(i).getObjectUID() == id) { return true; }
		}
		return false;
	}

	/**
	 * Get the minimal prices of the selected item
	 * 
	 * @return String : jsonarray of the prices
	 */
	public String getMinimalPrices() {
		return "[" + currentPrice.get(0) + "," + currentPrice.get(1) + "," + currentPrice.get(2) + "]";
	}

	/**
	 * Get all the unique id of one item depending on the quantity
	 * 
	 * @param int : general id
	 * @return List<Integer> : all the unique ids
	 */
	public List<Integer> getUidFromSeller(int guid, int quantity) {
		List<Integer> list = new ArrayList<>();
		for (ObjectItemToSellInBid item : this.itemsToSell) {
			if (item.getObjectGID() == guid && item.getQuantity() == quantity) {
				list.add(item.getObjectUID());
			}
		}
		return list;
	}

	public List<GameRolePlayNpcInformations> getNpc() {
		return npc;
	}

	public void setNpc(List<GameRolePlayNpcInformations> npc) {
		this.npc = npc;
	}

	public boolean isDialogOver() {
		return dialogOver;
	}

	public void setDialogOver(boolean dialogOver) {
		this.dialogOver = dialogOver;
	}

	public List<ObjectItemToSellInBid> getItemsToSell() {
		return itemsToSell;
	}

	public void removeItemToSell(int id) {
		for (int i = 0; i < itemsToSell.size(); i++) {
			if (itemsToSell.get(i).getObjectUID() == id) {
				itemsToSell.remove(i);
			}
		}
	}

	public void setItemsToSell(List<ObjectItemToSellInBid> itemsToSell) {
		this.itemsToSell = itemsToSell;
	}

	public List<Integer> getCanSell() {
		return canSell;
	}

	public void setCanSell(List<Integer> canSell) {
		this.canSell = canSell;
	}

	public List<Long> getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(List<Long> currentprice) {
		this.currentPrice = currentprice;
	}

}
