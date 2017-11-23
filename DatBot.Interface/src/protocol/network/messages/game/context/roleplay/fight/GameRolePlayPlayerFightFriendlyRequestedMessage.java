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
public class GameRolePlayPlayerFightFriendlyRequestedMessage extends NetworkMessage {
	public static final int ProtocolId = 5937;

	public int fightId;
	public long sourceId;
	public long targetId;

	public GameRolePlayPlayerFightFriendlyRequestedMessage(){
	}

	public GameRolePlayPlayerFightFriendlyRequestedMessage(int fightId, long sourceId, long targetId){
		this.fightId = fightId;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.fightId);
			writer.writeVarLong(this.sourceId);
			writer.writeVarLong(this.targetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readInt();
			this.sourceId = reader.readVarLong();
			this.targetId = reader.readVarLong();
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
