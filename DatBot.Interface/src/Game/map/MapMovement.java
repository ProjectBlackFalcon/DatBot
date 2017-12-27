package Game.map;

import Game.Info;
import Game.movement.CellMovement;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.ChangeMapMessage;

public class MapMovement {

    private double oldId;
    private double newId;
    private CellMovement cellMovement;
    private Network network;

	public MapMovement(CellMovement move, int neighbourId, Network network) {
		this.cellMovement = move;
		this.oldId = (double) Map.Id;
		this.newId = neighbourId;
		this.network = network;
	}
	
    public void PerformChangement() throws Exception
    {
    	if(cellMovement != null)
    		this.cellMovement.performMovement();
		this.network.getInfo().setWaitForMov(false);
		this.network.append("Changement de map...",false);
		this.network.sendToServer(new ChangeMapMessage(newId,false), ChangeMapMessage.ProtocolId, "Changement de map...");
    }

}
