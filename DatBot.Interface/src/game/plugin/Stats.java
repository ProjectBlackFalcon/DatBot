package game.plugin;

import java.util.List;

import game.Info;
import main.communication.DisplayInfo;
import protocol.network.Network;
import protocol.network.messages.game.character.stats.CharacterStatsListMessage;
import protocol.network.messages.game.inventory.items.InventoryContentMessage;
import protocol.network.types.game.character.characteristic.CharacterBaseCharacteristic;
import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;
import protocol.network.types.game.context.roleplay.job.JobExperience;
import utils.GameData;

public class Stats {

	private InventoryContentMessage inventoryContentMessage;
	private CharacterStatsListMessage stats;
	private List<JobExperience> job;
	private Info info;
	private long timePacketRecv;

	public Stats(Network network)
	{
		this.info = network.getInfo();
	}
	
	public String getStatsBot() {
		String str = "{";
//		// Add weight
		str += "\"Lvl\" : " + this.info.getLvl() + ",";
		str += "\"Xp\" : " + stats.getStats().getExperience() + ",";
		str += "\"XpNextLevelFloor\" : " + stats.getStats().getExperienceNextLevelFloor() + ",";
		str += "\"Weight\" : " + this.info.getWeight() + ",";
		str += "\"WeightMax\" : " + this.info.getWeigthMax() + ",";

		int hpPrct = 100;
		long diffTime = System.currentTimeMillis() / 1000 - timePacketRecv;
		if(2*diffTime + stats.getStats().getLifePoints() < stats.getStats().getMaxLifePoints()){
			hpPrct = (int) ((double)(2*diffTime + stats.getStats().getLifePoints()) / stats.getStats().getMaxLifePoints() * 100) ;
		}
		
		str += "\"Health\" : " + hpPrct + ",";
		
		// Job
		str += "\"Job\" : {";
		for (int i = 0; i < this.job.size(); i++) {
			if (i == job.size() - 1) {
				str += "\"" + job.get(i).getJobId() + "\" : [" + job.get(i).getJobLevel() + "," + job.get(i).getJobXP()
						+ "," + job.get(i).getJobXpLevelFloor() + "," + job.get(i).getJobXpNextLevelFloor() + "]}";
			} else {
				str += "\"" + job.get(i).getJobId() + "\" : [" + job.get(i).getJobLevel() + "," + job.get(i).getJobXP()
						+ "," + job.get(i).getJobXpLevelFloor() + "," + job.get(i).getJobXpNextLevelFloor() + "],";
			}
		}
		
		CharacterCharacteristicsInformations caracs = stats.getStats();
		
		// Stats 
		str += ",\"Caracs\" : {";
		str += addCarac("Int",caracs.getIntelligence()) + ",";
		str += addCarac("Agi",caracs.getAgility()) + ",";
		str += addCarac("Cha",caracs.getChance()) + ",";
		str += addCarac("Fo",caracs.getStrength()) + ",";
		str += addCarac("Vi",caracs.getVitality()) + ",";
		str += addCarac("Sa",caracs.getWisdom()) + ",";
		str += "\"Available\" : " + caracs.getStatsPoints() + "}";

		// Inventory

		str += ",\"Inventory\" : {";
		str += "\"Kamas\" : " + this.getInventoryContentMessage().getKamas() + ",";
		str += "\"Items\" : [";
		for (int i = 0; i < this.getInventoryContentMessage().getObjects().size(); i++) {
			if (i == this.getInventoryContentMessage().getObjects().size() - 1) {
				str += "[" + "\"" + DisplayInfo.cleanString(GameData.getItemName(this.getInventoryContentMessage().getObjects().get(i).getObjectGID())) + "\"" + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getObjectGID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getObjectUID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getQuantity() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getPosition() + "]";
			} else {
				str += "[" + "\"" + DisplayInfo.cleanString(GameData.getItemName(this.getInventoryContentMessage().getObjects().get(i).getObjectGID())) + "\"" + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getObjectGID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getObjectUID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getQuantity() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getPosition() + "],";
			}
		}
		str += "]}";

		str += "}";
		return str;

	}
	
	private String addCarac(String string, CharacterBaseCharacteristic intelligence) {
		return "\"" + string + "\" : [" + intelligence.getBase() + "," + intelligence.getAdditionnal()
			+ "," + intelligence.getObjectsAndMountBonus() + "]";
	}

	/**
	 * Check if the player has the item or not
	 * @param int : id of the item
	 * @return boolean
	 */
	public boolean haveItem(int id){
		return this.inventoryContentMessage.getObjects().contains(id);
	}

	public InventoryContentMessage getInventoryContentMessage()
	{
		return inventoryContentMessage;
	}

	public void setInventoryContentMessage(InventoryContentMessage inventoryContentMessage)
	{
		this.inventoryContentMessage = inventoryContentMessage;
	}

	public List<JobExperience> getJob() {
		return job;
	}

	public void setJob(List<JobExperience> job) {
		this.job = job;
	}
	
	public CharacterStatsListMessage getStats() {
		return stats;
	}

	public void setStats(CharacterStatsListMessage stats) {
		this.stats = stats;
	}

	public long getTimePacketRecv() {
		return timePacketRecv;
	}

	public void setTimePacketRecv(long timePacketRecv) {
		this.timePacketRecv = timePacketRecv;
	}

}
