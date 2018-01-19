package game.map;

import game.Info;
import game.movement.CellMovement;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.ChangeMapMessage;

public class MapMovement {

    private double oldId;
    private double newId;
    private CellMovement cellMovement;
    private Network network;

	public MapMovement(CellMovement move, int neighbourId, Network network) {
		this.cellMovement = move;
		this.oldId = (double) this.network.getMap().getId();
		this.newId = neighbourId;
		this.network = network;
	}
	
    public void PerformChangement() throws Exception
    {
    	if(cellMovement != null)
    		this.cellMovement.performMovement();
		this.network.getInfo().setWaitForMov(false);
		Network.append("Changing map...");
		this.network.sendToServer(new ChangeMapMessage(newId,false), ChangeMapMessage.ProtocolId, "Changing map...");
    }

}
