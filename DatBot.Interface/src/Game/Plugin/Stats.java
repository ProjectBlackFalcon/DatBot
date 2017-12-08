package Game.Plugin;

import Game.Info;
import protocol.network.messages.game.inventory.items.InventoryContentMessage;

public class Stats {
	
	public static InventoryContentMessage inventoryContentMessage;
	
	public static String getStats(){
		String str = "{";
		// Add weight
		str += "\"Lvl\" : " + Info.lvl + ",";
		str += "\"Xp\" : " + Info.stats.stats.experience + ",";
		str += "\"XpNextLevelFloor\" : " + Info.stats.stats.experienceNextLevelFloor + ",";
		str += "\"Weigth\" : " + Info.weight + ",";
		str += "\"WeigthMax\" : " + Info.weigthMax + ",";
		
		// Job
		str += "\"Job\" : {";
		for(int i = 0; i< Info.job.size() ; i++){
			if(i == Info.job.size()-1){
				str += "\"" + Info.job.get(i).jobId  +"\" : [" + Info.job.get(i).jobLevel + "," +  Info.job.get(i).jobXP + "," + Info.job.get(i).jobXpLevelFloor +  "," + Info.job.get(i).jobXpNextLevelFloor + "]}";
			} else {
				str += "\"" + Info.job.get(i).jobId  +"\" : [" + Info.job.get(i).jobLevel + "," +  Info.job.get(i).jobXP + "," + Info.job.get(i).jobXpLevelFloor +  "," + Info.job.get(i).jobXpNextLevelFloor + "],";
			}
		}
		
		// Inventory
		
		str += ",\"Inventory\" : {";
		str += "\"Kamas\" : " + inventoryContentMessage.kamas + ",";
		str += "\"Items\" : [";
		for(int i = 0; i< inventoryContentMessage.objects.size() ; i++){
			if(i == inventoryContentMessage.objects.size()-1){
				str += "[" + inventoryContentMessage.objects.get(i).objectGID + "," + inventoryContentMessage.objects.get(i).objectUID + "," + inventoryContentMessage.objects.get(i).quantity + "]";
			} else {
				str += "[" + inventoryContentMessage.objects.get(i).objectGID + "," + inventoryContentMessage.objects.get(i).objectUID + "," + inventoryContentMessage.objects.get(i).quantity + "],";
			}
		}
		str += "]}";

		str += "}";
		return str;
		
	}

}
