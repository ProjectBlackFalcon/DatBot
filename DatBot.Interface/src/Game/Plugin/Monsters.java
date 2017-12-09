package Game.Plugin;

import java.util.ArrayList;
import java.util.List;

import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterInformations;

public class Monsters {
	
	public static List<GameRolePlayGroupMonsterInformations> monsters = new ArrayList<GameRolePlayGroupMonsterInformations>();
	
	public static String getMonsters(){
		String str = "";
		str += "\"Monsters\" : [";
		for (int i = 0; i < monsters.size(); i++) {
			if (i == monsters.size() - 1) {
				str += "[" + monsters.get(i).contextualId + "," + monsters.get(i).staticInfos.mainCreatureLightInfos.creatureGenericId + "," + monsters.get(i).disposition.cellId + "]";
			} else {
				str += "[" + monsters.get(i).contextualId + "," + monsters.get(i).staticInfos.mainCreatureLightInfos.creatureGenericId + "," + monsters.get(i).disposition.cellId + "],";
			}
		}
		str += "]";
		return str;	
	}


}
