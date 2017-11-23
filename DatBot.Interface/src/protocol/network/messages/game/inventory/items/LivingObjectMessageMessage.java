package protocol.network.messages.game.inventory.items;

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
public class LivingObjectMessageMessage extends NetworkMessage {
	public static final int ProtocolId = 6065;

	public int msgId;
	public int timeStamp;
	public String owner;
	public int objectGenericId;

	public LivingObjectMessageMessage(){
	}

	public LivingObjectMessageMessage(int msgId, int timeStamp, String owner, int objectGenericId){
		this.msgId = msgId;
		this.timeStamp = timeStamp;
		this.owner = owner;
		this.objectGenericId = objectGenericId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.msgId);
			writer.writeInt(this.timeStamp);
			writer.writeUTF(this.owner);
			writer.writeVarShort(this.objectGenericId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.msgId = reader.readVarShort();
			this.timeStamp = reader.readInt();
			this.owner = reader.readUTF();
			this.objectGenericId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("msgId : " + this.msgId);
		//Network.appendDebug("timeStamp : " + this.timeStamp);
		//Network.appendDebug("owner : " + this.owner);
		//Network.appendDebug("objectGenericId : " + this.objectGenericId);
	//}
}
