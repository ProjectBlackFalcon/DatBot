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

	private int objectUID;
	private int position;
	private int quantity;

	public int getObjectUID() { return this.objectUID; }
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };
	public int getPosition() { return this.position; }
	public void setPosition(int position) { this.position = position; };
	public int getQuantity() { return this.quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; };

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
			writer.writeShort(this.position);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectUID = reader.readVarInt();
			this.position = reader.readShort();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
