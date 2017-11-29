package Game.Plugin;

import Game.Info;

public class Stats {
	
	public static String getStats(){
		String str = "{";
		// Add weight
		str += "\"Kamas\" : " + Info.stats.stats.kamas + ",";
		str += "\"Lvl\" : " + Info.lvl + ",";
		str += "\"Xp\" : " + Info.stats.stats.experience + ",";
		str += "\"Weigth\" : " + Info.weight + ",";
		str += "\"WeigthMax\" : " + Info.weigthMax + ",";

		return null;
		
	}

}
