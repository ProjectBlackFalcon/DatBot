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
public class ObjectFeedMessage extends NetworkMessage {
	public static final int ProtocolId = 6290;

	public int objectUID;
	public int foodUID;
	public int foodQuantity;

	public ObjectFeedMessage(){
	}

	public ObjectFeedMessage(int objectUID, int foodUID, int foodQuantity){
		this.objectUID = objectUID;
		this.foodUID = foodUID;
		this.foodQuantity = foodQuantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
			writer.writeVarInt(this.foodUID);
			writer.writeVarInt(this.foodQuantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectUID = reader.readVarInt();
			this.foodUID = reader.readVarInt();
			this.foodQuantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("objectUID : " + this.objectUID);
		//Network.appendDebug("foodUID : " + this.foodUID);
		//Network.appendDebug("foodQuantity : " + this.foodQuantity);
	//}
}
