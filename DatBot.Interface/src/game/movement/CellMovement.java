package game.movement;

import java.util.List;

import game.Info;
import game.map.MapMovementAdapter;
import game.movement.MovementVelocity.MovementTypeEnum;
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

		this.network.getInfo().setWaitForMov(false);

		List<Integer> keys = MapMovementAdapter.GetServerMovement(path);
		this.network.sendToServer(new GameMapMovementRequestMessage(keys, this.network.getInfo().getMapId()), GameMapMovementRequestMessage.ProtocolId, "D�placement...");
		if (!this.network.getInfo().isJoinedFight()){
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
			this.network.getInfo().setWaitForMov(true);
			this.network.sendToServer(new GameMapMovementConfirmMessage(), GameMapMovementConfirmMessage.ProtocolId, "Confirm...");
		} else {
			if (path.Cells.size() >= 3)
			{
				int time = MovementVelocity.GetPathVelocity(path, MovementTypeEnum.RUNNING);
				Thread.sleep(time);
			}
			else
			{
				int time = MovementVelocity.GetPathVelocity(path, MovementTypeEnum.WALKING);
				Thread.sleep(time);
			}
			this.network.getInfo().setWaitForMov(true);
		}
	}
}
