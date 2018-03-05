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
			s += "\"inPaddock\":" + "True" ;
			s += "}";
			if(i != stable.size() - 1){
				s += ",";
			}
		}
		return s;
	}

}
