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
public class GameRolePlayPlayerFightFriendlyAnsweredMessage extends NetworkMessage {
	public static final int ProtocolId = 5733;

	public int fightId;
	public long sourceId;
	public long targetId;
	public boolean accept;

	public GameRolePlayPlayerFightFriendlyAnsweredMessage(){
	}

	public GameRolePlayPlayerFightFriendlyAnsweredMessage(int fightId, long sourceId, long targetId, boolean accept){
		this.fightId = fightId;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.accept = accept;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			writer.writeVarLong(this.sourceId);
			writer.writeVarLong(this.targetId);
			writer.writeBoolean(this.accept);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.sourceId = reader.readVarLong();
			this.targetId = reader.readVarLong();
			this.accept = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("sourceId : " + this.sourceId);
		//Network.appendDebug("targetId : " + this.targetId);
		//Network.appendDebug("accept : " + this.accept);
	//}
}
