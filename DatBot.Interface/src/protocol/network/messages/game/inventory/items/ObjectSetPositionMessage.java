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
public class ObjectSetPositionMessage extends NetworkMessage {
	public static final int ProtocolId = 3021;

	public int objectUID;
	public int position;
	public int quantity;

	public ObjectSetPositionMessage(){
	}

	public ObjectSetPositionMessage(int objectUID, int position, int quantity){
		this.objectUID = objectUID;
		this.position = position;
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
			writer.writeByte(this.position);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectUID = reader.readVarInt();
			this.position = reader.readByte();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("objectUID : " + this.objectUID);
		//Network.appendDebug("position : " + this.position);
		//Network.appendDebug("quantity : " + this.quantity);
	//}
}
