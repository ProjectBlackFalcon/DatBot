package Game.map;

import Game.InfoAccount;
import Game.movement.CellMovement;
import Main.MainPlugin;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.ChangeMapMessage;

public class MapMovement {

    private double oldId;
    private double newId;
    private CellMovement cellMovement;

	public MapMovement(CellMovement move, int neighbourId) {
		this.cellMovement = move;
		this.oldId = (double) Map.Id;
		this.newId = neighbourId;
	}
	
    public void PerformChangement() throws Exception
    {
		InfoAccount.waitForMov = false;
    	this.cellMovement.performMovement();
		MainPlugin.frame.append("Changement de map...");
		Network.sendToServer(new ChangeMapMessage(newId), ChangeMapMessage.ProtocolId, "Changement de map...");
    }

}
