package protocol.network.messages.game.context.roleplay.fight;

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

@SuppressWarnings("unused")
public class GameRolePlayFightRequestCanceledMessage extends NetworkMessage {
	public static final int ProtocolId = 5822;

	public int fightId;
	public double sourceId;
	public double targetId;

	public GameRolePlayFightRequestCanceledMessage(){
	}

	public GameRolePlayFightRequestCanceledMessage(int fightId, double sourceId, double targetId){
		this.fightId = fightId;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			writer.writeDouble(this.sourceId);
			writer.writeDouble(this.targetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.sourceId = reader.readDouble();
			this.targetId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("sourceId : " + this.sourceId);
		//Network.appendDebug("targetId : " + this.targetId);
	//}
}
