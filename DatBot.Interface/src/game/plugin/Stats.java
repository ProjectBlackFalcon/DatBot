package game.plugin;

import game.Info;
import protocol.network.Network;
import protocol.network.messages.game.inventory.items.InventoryContentMessage;

public class Stats {

	private InventoryContentMessage inventoryContentMessage;
	private Network network;
	private Info info;

	public Stats(Network network)
	{
		this.network = network;
		this.info = network.getInfo();
	}

	public String getStats() {
		String str = "{";
		// Add weight
		str += "\"Lvl\" : " + this.info.getLvl() + ",";
		str += "\"Xp\" : " + this.info.getStats().getStats().getExperience() + ",";
		str += "\"XpNextLevelFloor\" : " + this.info.getStats().getStats().getExperienceNextLevelFloor() + ",";
		str += "\"Weigth\" : " + this.info.getWeight() + ",";
		str += "\"WeigthMax\" : " + this.info.getWeigthMax() + ",";

		// Job
		str += "\"Job\" : {";
		for (int i = 0; i < this.info.getJob().size(); i++) {
			if (i == this.info.getJob().size() - 1) {
				str += "\"" + this.info.getJob().get(i).getJobId() + "\" : [" + this.info.getJob().get(i).getJobLevel() + "," + this.info.getJob().get(i).getJobXP()
						+ "," + this.info.getJob().get(i).getJobXpLevelFloor() + "," + this.info.getJob().get(i).getJobXpNextLevelFloor() + "]}";
			} else {
				str += "\"" + this.info.getJob().get(i).getJobId() + "\" : [" + this.info.getJob().get(i).getJobLevel() + "," + this.info.getJob().get(i).getJobXP()
						+ "," + this.info.getJob().get(i).getJobXpLevelFloor() + "," + this.info.getJob().get(i).getJobXpNextLevelFloor() + "],";
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

}
