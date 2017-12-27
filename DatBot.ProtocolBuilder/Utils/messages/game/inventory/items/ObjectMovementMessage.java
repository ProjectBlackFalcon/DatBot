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
public class ObjectMovementMessage extends NetworkMessage {
	public static final int ProtocolId = 3010;

	private int objectUID;
	private int position;

	public int getObjectUID() { return this.objectUID; };
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };
	public int getPosition() { return this.position; };
	public void setPosition(int position) { this.position = position; };

	public ObjectMovementMessage(){
	}

	public ObjectMovementMessage(int objectUID, int position){
		this.objectUID = objectUID;
		this.position = position;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
			writer.writeByte(this.position);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectUID = reader.readVarInt();
			this.position = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
