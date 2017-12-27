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
public class ObjectItemGenericQuantity extends Item {
	public static final int ProtocolId = 483;

	private int objectGID;
	private int quantity;

	public int getObjectGID() { return this.objectGID; };
	public void setObjectGID(int objectGID) { this.objectGID = objectGID; };
	public int getQuantity() { return this.quantity; };
	public void setQuantity(int quantity) { this.quantity = quantity; };

	public ObjectItemGenericQuantity(){
	}

	public ObjectItemGenericQuantity(int objectGID, int quantity){
		this.objectGID = objectGID;
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.objectGID);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectGID = reader.readVarShort();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
