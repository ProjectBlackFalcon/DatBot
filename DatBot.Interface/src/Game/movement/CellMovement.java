package Game.movement;

import java.util.List;
import Game.Info;
import Game.map.MapMovementAdapter;
import Game.movement.MovementVelocity.MovementTypeEnum;
import protocol.network.Network;
import protocol.network.messages.game.context.GameMapMovementConfirmMessage;
import protocol.network.messages.game.context.GameMapMovementRequestMessage;

public class CellMovement {

	public int startCell;
	public int endCell;
	public MovementPath path;
	private Network network;

	public CellMovement(MovementPath path, Network network)
	{
		// Movement
		this.startCell = path.CellStart.CellId;
		this.endCell = path.CellEnd.CellId;
		this.path = path;
		this.network = network;
	}

	public void performMovement() throws Exception
	{
		if (path == null) return;

		Info.waitForMov = false;

		List<Integer> keys = MapMovementAdapter.GetServerMovement(path);
		this.network.sendToServer(new GameMapMovementRequestMessage(keys, Info.mapId), GameMapMovementRequestMessage.ProtocolId, "Déplacement...");
		if (path.Cells.size() >= 4)
		{
			int time = MovementVelocity.GetPathVelocity(path, MovementTypeEnum.RUNNING);
			Thread.sleep(time);
		}
		else
		{
			int time = MovementVelocity.GetPathVelocity(path, MovementTypeEnum.WALKING);
			Thread.sleep(time);
		}
		Info.waitForMov = true;
		
		if (!Info.joinedFight)
		{
			this.network.sendToServer(new GameMapMovementConfirmMessage(), GameMapMovementConfirmMessage.ProtocolId, "Confirm...");
		}
	}
}
