package protocol.network.messages.game.inventory.storage;

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
public class StorageObjectRemoveMessage extends NetworkMessage {
	public static final int ProtocolId = 5648;

	private int objectUID;

	public int getObjectUID() { return this.objectUID; }
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };

	public StorageObjectRemoveMessage(){
	}

	public StorageObjectRemoveMessage(int objectUID){
		this.objectUID = objectUID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectUID = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
