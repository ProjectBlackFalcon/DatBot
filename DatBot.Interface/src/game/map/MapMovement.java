package game.map;

import game.movement.CellMovement;
import main.communication.Communication;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.ChangeMapMessage;

public class MapMovement {

	private double oldId;
	private double newId;
	private CellMovement cellMovement;
	private Network network;

	public MapMovement(CellMovement move, int neighbourId, Network network) {
		this.cellMovement = move;
		this.network = network;
		this.oldId = (double) this.network.getMap().getId();
		this.newId = neighbourId;
	}

	public void PerformChangement(Network network) throws Exception {
		this.network = network;
		if (cellMovement != null) this.cellMovement.performMovement();
		this.network.getInfo().setWaitForMov(false);
		network.append("Changing map...");
		triggerChangeMap();		
	}

	private void triggerChangeMap() throws Exception {
		long index = System.currentTimeMillis();
		while (!this.network.getInfo().isBasicNoOperationMsg()) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - index > 10000) {
				this.network.getLog().writeActionLogMessage("ChangeMapMessage", "error while performing changeMap");
				return;
			}
		}
		if(!this.network.getInfo().isCurrentMapTrigger() && this.network.getMap().getId() == oldId){
			this.network.getLog().writeActionLogMessage("ChangeMapMessage", "Changing map");
			this.network.sendToServer(new ChangeMapMessage(newId, false), ChangeMapMessage.ProtocolId, "Changing map...");
		} else {
			this.network.getLog().writeActionLogMessage("ChangeMapMessage", "CurrentMapMessage triggered, the player is already on the next map");
		}

	}

	public CellMovement getCellMovement() {
		return cellMovement;
	}

}
