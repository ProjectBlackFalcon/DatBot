package Game.Plugin;

import Game.Info;
import protocol.network.messages.game.inventory.storage.StorageInventoryContentMessage;
import protocol.network.types.game.data.items.ObjectItem;
import protocol.network.types.game.interactive.InteractiveElement;
import protocol.network.types.game.interactive.InteractiveElementSkill;

public class Bank {
	
	public static int cellIdAstrubIN = 317;
	public static int interactiveAstrubIN = 465440;
	public static int cellIdAstrubOUT = 396;
	
	public static int cellIdBontaIN = 353;
	public static int interactiveBontaIN = 433934;
	public static int cellIdBontaOUT = 424;
	
	public static int cellIdBrakmarIN = 246;
	public static int interactiveBrakmarIN = 415350;
	public static int cellIdBrakmarOUT = 424;
	
	public static StorageInventoryContentMessage storage;
	
	public static int getSkill(int interactive){
		for (InteractiveElement i : Interactive.interactiveElements) {
			if(i.elementId == interactive){
				return i.enabledSkills.get(0).skillInstanceUid;
			}
		}
		return -1;
	}
	
	public static String getBank(){
		if(storage == null){
			return "";
		}
		String str = "{";
		str += "\"Kamas\" : " + storage.kamas + ",";
		str += "\"Items\" : [";
		for(int i = 0; i< storage.objects.size() ; i++){
			if(i == storage.objects.size()-1){
				str += "[" + storage.objects.get(i).objectGID + "," + storage.objects.get(i).objectUID + "," + storage.objects.get(i).quantity + "]";
			} else {
				str += "[" + storage.objects.get(i).objectGID + "," + storage.objects.get(i).objectUID + "," + storage.objects.get(i).quantity + "],";
			}
		}
		str += "]}";
		return str;
	}

}
