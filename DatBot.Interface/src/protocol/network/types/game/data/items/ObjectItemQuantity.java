package protocol.network.types.game.data.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.Item;

@SuppressWarnings("unused")
public class ObjectItemQuantity extends Item {
	public static final int ProtocolId = 119;

	public int objectUID;
	public int quantity;

	public ObjectItemQuantity(){
	}

	public ObjectItemQuantity(int objectUID, int quantity){
		this.objectUID = objectUID;
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.objectUID);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectUID = reader.readVarInt();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("objectUID : " + this.objectUID);
		//Network.appendDebug("quantity : " + this.quantity);
	//}
}
