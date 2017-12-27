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

	private int fightId;
	private long sourceId;
	private long targetId;

	public int getFightId() { return this.fightId; };
	public void setFightId(int fightId) { this.fightId = fightId; };
	public long getSourceId() { return this.sourceId; };
	public void setSourceId(long sourceId) { this.sourceId = sourceId; };
	public long getTargetId() { return this.targetId; };
	public void setTargetId(long targetId) { this.targetId = targetId; };

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
			writer.writeVarShort(this.fightId);
			writer.writeVarLong(this.sourceId);
			writer.writeVarLong(this.targetId);
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
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
