package Game.Plugin;

import Game.Info;

public class Stats {
	
	public static String getStats(){
		String str = "{";
		// Add weight
		str += "\"Kamas\" : " + Info.stats.stats.kamas + ",";
		str += "\"Lvl\" : " + Info.lvl + ",";
		str += "\"Xp\" : " + Info.stats.stats.experience + ",";
		str += "\"XpNextLevelFloor\" : " + Info.stats.stats.experienceNextLevelFloor + ",";
		str += "\"Weigth\" : " + Info.weight + ",";
		str += "\"WeigthMax\" : " + Info.weigthMax + ",";
		
		//Job
		str += "\"Job\" : {";
		for(int i = 0; i< Info.job.size() ; i++){
			if(i == Info.job.size()-1){
				str += "\"" + Info.job.get(i).jobId  +"\" : [" + Info.job.get(i).jobLevel + "," +  Info.job.get(i).jobXP + "," + Info.job.get(i).jobXpLevelFloor +  "," + Info.job.get(i).jobXpNextLevelFloor + "]}";
			} else {
				str += "\"" + Info.job.get(i).jobId  +"\" : [" + Info.job.get(i).jobLevel + "," +  Info.job.get(i).jobXP + "," + Info.job.get(i).jobXpLevelFloor +  "," + Info.job.get(i).jobXpNextLevelFloor + "],";
			}
		}
		str += "}";
		return str;
		
	}

}
