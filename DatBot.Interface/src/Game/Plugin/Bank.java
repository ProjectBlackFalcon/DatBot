package Game.Plugin;

import Game.Info;
import protocol.network.messages.game.inventory.storage.StorageInventoryContentMessage;
import protocol.network.types.game.data.items.ObjectItem;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.InteractiveElementSkill;

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

	public StorageInventoryContentMessage storage;

	public String getBank() {
		if (storage == null) {
			return "";
		}
		String str = "{";
		str += "\"Kamas\" : " + storage.kamas + ",";
		str += "\"Items\" : [";
		for (int i = 0; i < storage.objects.size(); i++) {
			if (i == storage.objects.size() - 1) {
				str += "[" + storage.objects.get(i).objectGID + "," + storage.objects.get(i).objectUID + ","
						+ storage.objects.get(i).quantity + "," + storage.objects.get(i).position + "]";
			} else {
				str += "[" + storage.objects.get(i).objectGID + "," + storage.objects.get(i).objectUID + ","
						+ storage.objects.get(i).quantity + "," + storage.objects.get(i).position + "],";
			}
		}
		str += "]}";
		return str;
	}

}
