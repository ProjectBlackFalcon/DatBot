package game.plugin;

import java.util.ArrayList;
import java.util.List;

import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterInformations;

public class Monsters {
	
	private List<GameRolePlayGroupMonsterInformations> monsters = new ArrayList<GameRolePlayGroupMonsterInformations>();
	
	public Monsters(){
		
	}
	
	public String toString(){
		String str = "";
		for (int i = 0; i < this.getMonsters().size(); i++) {
			if (i == this.getMonsters().size() - 1) {
				str += "[" + this.getMonsters().get(i).getContextualId() + "," + this.getMonsters().get(i).getStaticInfos().getMainCreatureLightInfos().getCreatureGenericId() + "," + this.getMonsters().get(i).getDisposition().getCellId() + "]";
			} else {
				str += "[" + this.getMonsters().get(i).getContextualId() + "," + this.getMonsters().get(i).getStaticInfos().getMainCreatureLightInfos().getCreatureGenericId() + "," + this.getMonsters().get(i).getDisposition().getCellId() + "],";
			}
		}
		return str;	
	}

	public void setMonsters(List<GameRolePlayGroupMonsterInformations> monsters)
	{
		this.monsters = monsters;
	}

	public List<GameRolePlayGroupMonsterInformations> getMonsters()
	{
		return monsters;
	}
	
	public void addMonsters(GameRolePlayGroupMonsterInformations monster){
		this.monsters.add(monster);
	}


}
