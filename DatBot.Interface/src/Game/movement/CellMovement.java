package Game.movement;

import java.util.List;
import Game.InfoAccount;
import Game.map.MapMovementAdapter;
import Game.movement.MovementVelocity.MovementTypeEnum;
import Main.MainPlugin;
import protocol.network.Network;
import protocol.network.messages.game.context.GameMapMovementConfirmMessage;
import protocol.network.messages.game.context.GameMapMovementRequestMessage;
import utils.JSON;

public class CellMovement {

	public int startCell;
	public int endCell;
	private MovementPath path;

	public CellMovement(MovementPath path) {
		// Movement
		this.startCell = path.CellStart.CellId;
		this.endCell = path.CellEnd.CellId;
		this.path = path;
	}

	public void performMovement() throws Exception {
		if (path == null)
			throw new Error("No path");
		
		List<Integer> keys = MapMovementAdapter.GetServerMovement(path); 
		Network.sendToServer(new GameMapMovementRequestMessage(keys, InfoAccount.mapId), GameMapMovementRequestMessage.ProtocolId, "D�placement...");
		if(path.Cells.size() >= 4 ){
			int time = MovementVelocity.GetPathVelocity(path, MovementTypeEnum.RUNNING);
			Thread.sleep(time);
		} else {
			int time = MovementVelocity.GetPathVelocity(path, MovementTypeEnum.WALKING);
			Thread.sleep(time);
		}
		Network.sendToServer(new GameMapMovementConfirmMessage(), GameMapMovementConfirmMessage.ProtocolId, "Confirm...");
	}
}