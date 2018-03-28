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
public class GameRolePlayPlayerFightRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5731;

	private long targetId;
	private int targetCellId;
	private boolean friendly;

	public long getTargetId() { return this.targetId; }
	public void setTargetId(long targetId) { this.targetId = targetId; };
	public int getTargetCellId() { return this.targetCellId; }
	public void setTargetCellId(int targetCellId) { this.targetCellId = targetCellId; };
	public boolean isFriendly() { return this.friendly; }
	public void setFriendly(boolean friendly) { this.friendly = friendly; };

	public GameRolePlayPlayerFightRequestMessage(){
	}

	public GameRolePlayPlayerFightRequestMessage(long targetId, int targetCellId, boolean friendly){
		this.targetId = targetId;
		this.targetCellId = targetCellId;
		this.friendly = friendly;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.targetId);
			writer.writeShort(this.targetCellId);
			writer.writeBoolean(this.friendly);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.targetId = reader.readVarLong();
			this.targetCellId = reader.readShort();
			this.friendly = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
