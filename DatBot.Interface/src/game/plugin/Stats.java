package game.plugin;

import java.util.ArrayList;
import java.util.List;

import game.Info;
import protocol.network.Network;
import protocol.network.messages.game.character.stats.CharacterStatsListMessage;
import protocol.network.messages.game.inventory.items.InventoryContentMessage;
import protocol.network.types.game.context.roleplay.job.JobExperience;

public class Stats {

	private InventoryContentMessage inventoryContentMessage;
	private CharacterStatsListMessage stats;
	private List<JobExperience> job = new ArrayList<JobExperience>();
	private Network network;
	private Info info;

	public Stats(Network network)
	{
		this.network = network;
		this.info = network.getInfo();
	}

	public String toString() {
		String str = "{";
		// Add weight
		str += "\"Lvl\" : " + this.info.getLvl() + ",";
		str += "\"Xp\" : " + stats.getStats().getExperience() + ",";
		str += "\"XpNextLevelFloor\" : " + stats.getStats().getExperienceNextLevelFloor() + ",";
		str += "\"Weigth\" : " + this.info.getWeight() + ",";
		str += "\"WeigthMax\" : " + this.info.getWeigthMax() + ",";

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

		// Inventory

		str += ",\"Inventory\" : {";
		str += "\"Kamas\" : " + this.getInventoryContentMessage().getKamas() + ",";
		str += "\"Items\" : [";
		for (int i = 0; i < this.getInventoryContentMessage().getObjects().size(); i++) {
			if (i == this.getInventoryContentMessage().getObjects().size() - 1) {
				str += "[" + this.getInventoryContentMessage().getObjects().get(i).getObjectGID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getObjectUID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getQuantity() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getPosition() + "]";
			} else {
				str += "[" + this.getInventoryContentMessage().getObjects().get(i).getObjectGID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getObjectUID() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getQuantity() + ","
						+ this.getInventoryContentMessage().getObjects().get(i).getPosition() + "],";
			}
		}
		str += "]}";

		str += "}";
		return str;

	}

	public InventoryContentMessage getInventoryContentMessage()
	{
		return inventoryContentMessage;
	}

	public void setInventoryContentMessage(InventoryContentMessage inventoryContentMessage)
	{
		this.inventoryContentMessage = inventoryContentMessage;
	}

	public CharacterStatsListMessage getStats() {
		return stats;
	}

	public void setStats(CharacterStatsListMessage stats) {
		this.stats = stats;
	}

	public List<JobExperience> getJob() {
		return job;
	}

	public void setJob(List<JobExperience> job) {
		this.job = job;
	}

}
