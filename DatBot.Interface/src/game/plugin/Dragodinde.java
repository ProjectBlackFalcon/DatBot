package game.plugin;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import protocol.network.types.game.mount.MountClientData;
import utils.GameData;

public class Dragodinde {
	
	private boolean inStable = false;
	private List<MountClientData> paddock;
	private List<MountClientData> stable;
	private boolean fart = false;
	
	/*
	 * Equiped dd
	 */
	private boolean havingDd = false;
	private int id;
	private int levelEquipedDD;
	private int energy;
	private int ratioXp; 
	
	
	public static int getActionId(String to, String from){
		switch(to){
			case "stable":
				switch(from){
					case "paddock":
						return 7;
					case "inventory":
						return 5;
					case "equip":
						return 1;
				}
				break;
			case "paddock":
				switch(from){
					case "stable":
						return 6;
					case "inventory":
						return 16;
					case "equip":
						return 9;
				}
				break;
			case "inventory":
				switch(from){
					case "paddock":
						return 14;
					case "stable":
						return 4;
					case "equip":
						return 13;
				}
				break;
			case "equip":
				switch(from){
					case "paddock":
						return 10;
					case "inventory":
						return 15;
					case "stable":
						return 2;
				}
				break;
		}
		return 0;
	}

	public boolean isInStable()
	{
		return inStable;
	}

	public void setInStable(boolean inStable)
	{
		this.inStable = inStable;
	}

	public List<MountClientData> getPaddock()
	{
		return paddock;
	}

	public void setPaddock(List<MountClientData> paddock)
	{
		this.paddock = paddock;
	}

	public List<MountClientData> getStable()
	{
		return stable;
	}

	public void setStable(List<MountClientData> stable)
	{
		this.stable = stable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString()
	{
		String s = "";
		if (paddock == null || stable == null)
			return "";
		for (int i = 0 ; i < paddock.size() ; i++)
		{
			s += "{";
			s += "\"id\":" + (int) paddock.get(i).getId() + "," ;
			s += "\"behaviours\":[";
			for (int i1 = 0 ; i1 < paddock.get(i).getBehaviors().size() ; i1++)
			{
				if(i1 == paddock.get(i).getBehaviors().size())
					s += "\"" + GameData.getMountBehaviorName(paddock.get(i).getBehaviors().get(i1)) + "\"";
				else
					s += "\"" + GameData.getMountBehaviorName(paddock.get(i).getBehaviors().get(i1)) + "\",";
			}
			s += "],";
			s += "\"name\":\"" + paddock.get(i).getName() + "\"," ;
			if(paddock.get(i).isSex())
				s += "\"sex\":True," ;
			else 
				s += "\"sex\":False," ;
			s += "\"experience\":" + paddock.get(i).getExperience() + "," ;
			s += "\"level\":" + paddock.get(i).getLevel() + "," ;
			s += "\"stamina\":" + paddock.get(i).getStamina() + "," ;
			s += "\"staminaMax\":" + paddock.get(i).getStaminaMax() + "," ;
			s += "\"maturity\":" + paddock.get(i).getMaturity() + "," ;
			s += "\"maturityForAdult\":" + paddock.get(i).getMaturityForAdult() + "," ;
			s += "\"energy\":" + paddock.get(i).getEnergy() + "," ;
			s += "\"energyMax\":" + paddock.get(i).getEnergyMax() + "," ;
			s += "\"serenity\":" + paddock.get(i).getSerenity() + "," ;
			s += "\"serenityMax\":" + paddock.get(i).getSerenityMax() + "," ;
			s += "\"aggressivityMax\":" + paddock.get(i).getAggressivityMax() + "," ;
			s += "\"love\":" + paddock.get(i).getLove() + "," ;
			s += "\"loveMax\":" + paddock.get(i).getLoveMax() + "," ;
			s += "\"fecondationTime\":" + paddock.get(i).getFecondationTime() + "," ;
			if(paddock.get(i).isIsFecondationReady())
				s += "\"isFecondationReady\":True," ;
			else 
				s += "\"isFecondationReady\":False," ;
			s += "\"boostLimiter\":" + paddock.get(i).getBoostLimiter() + "," ;
			s += "\"boostMax\":" + (int) paddock.get(i).getBoostMax() + "," ;
			s += "\"reproductionCount\":" + paddock.get(i).getReproductionCount() + "," ;
			s += "\"reproductionCountMax\":" + paddock.get(i).getReproductionCountMax() + "," ;
			s += "\"inPaddock\":" + "True" ;
			s += "}";
			if(i != paddock.size() - 1){
				s += ",";
			}
		}
		if(paddock.size() > 0){
			s += ",";
		}
		for (int i = 0 ; i < stable.size() ; i++)
		{
			s += "{";
			s += "\"id\":" + (int) stable.get(i).getId() + "," ;
			s += "\"behaviours\":[";
			for (int i1 = 0 ; i1 < stable.get(i).getBehaviors().size() ; i1++)
			{
				if(i1 == stable.get(i).getBehaviors().size())
					s += "\"" + GameData.getMountBehaviorName(stable.get(i).getBehaviors().get(i1)) + "\"";
				else
					s += "\"" + GameData.getMountBehaviorName(stable.get(i).getBehaviors().get(i1)) + "\",";
			}
			s += "],";
			s += "\"name\":\"" + stable.get(i).getName() + "\"," ;
			if(stable.get(i).isSex())
				s += "\"sex\":True," ;
			else 
				s += "\"sex\":False," ;
			s += "\"experience\":" + stable.get(i).getExperience() + "," ;
			s += "\"level\":" + stable.get(i).getLevel() + "," ;
			s += "\"stamina\":" + stable.get(i).getStamina() + "," ;
			s += "\"staminaMax\":" + stable.get(i).getStaminaMax() + "," ;
			s += "\"maturity\":" + stable.get(i).getMaturity() + "," ;
			s += "\"maturityForAdult\":" + stable.get(i).getMaturityForAdult() + "," ;
			s += "\"energy\":" + stable.get(i).getEnergy() + "," ;
			s += "\"energyMax\":" + stable.get(i).getEnergyMax() + "," ;
			s += "\"serenity\":" + stable.get(i).getSerenity() + "," ;
			s += "\"serenityMax\":" + stable.get(i).getSerenityMax() + "," ;
			s += "\"aggressivityMax\":" + stable.get(i).getAggressivityMax() + "," ;
			s += "\"love\":" + stable.get(i).getLove() + "," ;
			s += "\"loveMax\":" + stable.get(i).getLoveMax() + "," ;
			s += "\"fecondationTime\":" + stable.get(i).getFecondationTime() + "," ;
			if(stable.get(i).isIsFecondationReady())
				s += "\"isFecondationReady\":True," ;
			else 
				s += "\"isFecondationReady\":False," ;
			s += "\"boostLimiter\":" + stable.get(i).getBoostLimiter() + "," ;
			s += "\"boostMax\":" + (int) stable.get(i).getBoostMax() + "," ;
			s += "\"reproductionCount\":" + stable.get(i).getReproductionCount() + "," ;
			s += "\"reproductionCountMax\":" + stable.get(i).getReproductionCountMax() + "," ;
			s += "\"inPaddock\":" + "False" ;
			s += "}";
			if(i != stable.size() - 1){
				s += ",";
			}
		}
		return s;
	}
	
	public boolean isFart() {
		return fart;
	}

	public void setFart(boolean fart) {
		this.fart = fart;
	}

	public int getLevelEquipedDD() {
		return levelEquipedDD;
	}

	public void setLevelEquipedDD(int levelEquipedDD) {
		this.levelEquipedDD = levelEquipedDD;
	}

	public int getRatioXp() {
		return ratioXp;
	}

	public void setRatioXp(int ratioXp) {
		this.ratioXp = ratioXp;
	}

	public boolean isHavingDd() {
		return havingDd;
	}

	public void setHavingDd(boolean havingDd) {
		this.havingDd = havingDd;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
