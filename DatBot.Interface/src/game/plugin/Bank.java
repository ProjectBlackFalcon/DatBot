package game.plugin;

import game.Info;
import main.communication.DisplayInfo;
import protocol.network.messages.game.inventory.storage.StorageInventoryContentMessage;
import protocol.network.types.game.data.items.ObjectItem;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.InteractiveElementSkill;
import utils.GameData;

public class Bank {

	public int cellIdAstrubIN = 317;
	public int interactiveAstrubIN = 465440;
	public int cellIdAstrubOUT = 396;

	public int cellIdBontaIN = 353;
	public int interactiveBontaIN = 433934;
	public int cellIdBontaOUT = 424;

	public int cellIdBrakmarIN = 246;
	public int interactiveBrakmarIN = 415350;
	public int cellIdBrakmarOUT = 424;

	private StorageInventoryContentMessage storage;

	public String toString() {
		if (storage == null) {
			return "";
		}
		String str = "{";
		str += "\"Kamas\" : " + storage.getKamas() + ",";
		str += "\"Items\" : [";
		for (int i = 0; i < storage.getObjects().size(); i++) {
			if (i == storage.getObjects().size() - 1) {
				str += "[" + "\"" + DisplayInfo.cleanString(GameData.getItemName(storage.getObjects().get(i).getObjectGID())) + "\"" + ","
						+ GameData.getItemName(storage.getObjects().get(i).getObjectGID()) + "," 
						+ storage.getObjects().get(i).getObjectUID() + ","
						+ storage.getObjects().get(i).getQuantity() + "," + storage.getObjects().get(i).getPosition() + "," +  GameData.getPodsFromItem(storage.getObjects().get(i).getObjectGID()) + "]";
			} else {
				str += "[" + "\"" + DisplayInfo.cleanString(GameData.getItemName(storage.getObjects().get(i).getObjectGID())) + "\"" + "," 
						+ GameData.getItemName(storage.getObjects().get(i).getObjectGID()) + "," 
						+ storage.getObjects().get(i).getObjectUID() + ","
						+ storage.getObjects().get(i).getQuantity() + "," + storage.getObjects().get(i).getPosition() + "," +  GameData.getPodsFromItem(storage.getObjects().get(i).getObjectGID()) + "],";
			}
		}
		str += "]}";
		return str;
	}

	public StorageInventoryContentMessage getStorage()
	{
		return storage;
	}

	public void setStorage(StorageInventoryContentMessage storage)
	{
		this.storage = storage;
	}

}
