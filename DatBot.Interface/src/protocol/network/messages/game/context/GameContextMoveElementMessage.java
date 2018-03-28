package protocol.network.messages.game.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;
import protocol.network.types.game.context.EntityMovementInformations;

@SuppressWarnings("unused")
public class GameContextMoveElementMessage extends NetworkMessage {
	public static final int ProtocolId = 253;

	private EntityMovementInformations movement;

	public EntityMovementInformations getMovement() { return this.movement; }
	public void setMovement(EntityMovementInformations movement) { this.movement = movement; };

	public GameContextMoveElementMessage(){
	}

	public GameContextMoveElementMessage(EntityMovementInformations movement){
		this.movement = movement;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			movement.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.movement = new EntityMovementInformations();
			this.movement.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
