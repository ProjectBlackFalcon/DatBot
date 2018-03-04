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
			s += "]";
			s += "\"name\":" + stable.get(i).getName() + "," ;
			s += "\"sex\":" + stable.get(i).isSex() + "," ;
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
			s += "\"isFecondationReady\":" + stable.get(i).isIsFecondationReady() + "," ;
			s += "\"boostLimiter\":" + stable.get(i).getBoostLimiter() + "," ;
			s += "\"boostMax\":" + (int) stable.get(i).getBoostMax() + "," ;
			s += "\"reproductionCount\":" + stable.get(i).getReproductionCount() + "," ;
			s += "\"reproductionCountMax\":" + stable.get(i).getReproductionCountMax() + "," ;
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
			s += "]";
			s += "\"name\":" + stable.get(i).getName() + "," ;
			s += "\"sex\":" + stable.get(i).isSex() + "," ;
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
			s += "\"isFecondationReady\":" + stable.get(i).isIsFecondationReady() + "," ;
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

}
