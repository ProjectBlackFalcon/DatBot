package protocol.network.messages.game.interactive;

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
public class InteractiveUsedMessage extends NetworkMessage {
	public static final int ProtocolId = 5745;

	private long entityId;
	private int elemId;
	private int skillId;
	private int duration;
	private boolean canMove;

	public long getEntityId() { return this.entityId; };
	public void setEntityId(long entityId) { this.entityId = entityId; };
	public int getElemId() { return this.elemId; };
	public void setElemId(int elemId) { this.elemId = elemId; };
	public int getSkillId() { return this.skillId; };
	public void setSkillId(int skillId) { this.skillId = skillId; };
	public int getDuration() { return this.duration; };
	public void setDuration(int duration) { this.duration = duration; };
	public boolean isCanMove() { return this.canMove; };
	public void setCanMove(boolean canMove) { this.canMove = canMove; };

	public InteractiveUsedMessage(){
	}

	public InteractiveUsedMessage(long entityId, int elemId, int skillId, int duration, boolean canMove){
		this.entityId = entityId;
		this.elemId = elemId;
		this.skillId = skillId;
		this.duration = duration;
		this.canMove = canMove;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.entityId);
			writer.writeVarInt(this.elemId);
			writer.writeVarShort(this.skillId);
			writer.writeVarShort(this.duration);
			writer.writeBoolean(this.canMove);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.entityId = reader.readVarLong();
			this.elemId = reader.readVarInt();
			this.skillId = reader.readVarShort();
			this.duration = reader.readVarShort();
			this.canMove = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
