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

	public long entityId;
	public int elemId;
	public int skillId;
	public int duration;
	public boolean canMove;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("entityId : " + this.entityId);
		//Network.appendDebug("elemId : " + this.elemId);
		//Network.appendDebug("skillId : " + this.skillId);
		//Network.appendDebug("duration : " + this.duration);
		//Network.appendDebug("canMove : " + this.canMove);
	//}
}
