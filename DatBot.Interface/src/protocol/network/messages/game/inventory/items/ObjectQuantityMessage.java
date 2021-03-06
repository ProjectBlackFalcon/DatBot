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
public class ObjectQuantityMessage extends NetworkMessage {
	public static final int ProtocolId = 3023;

	private int objectUID;
	private int quantity;
	private int origin;

	public int getObjectUID() { return this.objectUID; }
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };
	public int getQuantity() { return this.quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; };
	public int getOrigin() { return this.origin; }
	public void setOrigin(int origin) { this.origin = origin; };

	public ObjectQuantityMessage(){
	}

	public ObjectQuantityMessage(int objectUID, int quantity, int origin){
		this.objectUID = objectUID;
		this.quantity = quantity;
		this.origin = origin;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
			writer.writeVarInt(this.quantity);
			writer.writeByte(this.origin);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectUID = reader.readVarInt();
			this.quantity = reader.readVarInt();
			this.origin = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
