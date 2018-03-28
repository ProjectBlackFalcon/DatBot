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

	private int objectUID;
	private int quantity;

	public int getObjectUID() { return this.objectUID; }
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };
	public int getQuantity() { return this.quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; };

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
	}

}
