package protocol.network.messages.game.context.roleplay.objects;

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
public class ObjectGroundAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 3017;

	private int cellId;
	private int objectGID;

	public int getCellId() { return this.cellId; }
	public void setCellId(int cellId) { this.cellId = cellId; };
	public int getObjectGID() { return this.objectGID; }
	public void setObjectGID(int objectGID) { this.objectGID = objectGID; };

	public ObjectGroundAddedMessage(){
	}

	public ObjectGroundAddedMessage(int cellId, int objectGID){
		this.cellId = cellId;
		this.objectGID = objectGID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.cellId);
			writer.writeVarShort(this.objectGID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cellId = reader.readVarShort();
			this.objectGID = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
