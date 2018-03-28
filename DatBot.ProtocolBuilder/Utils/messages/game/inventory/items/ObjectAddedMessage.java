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
import protocol.network.types.game.data.items.ObjectItem;

@SuppressWarnings("unused")
public class ObjectAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 3025;

	private ObjectItem object;
	private int origin;

	public ObjectItem getObject() { return this.object; }
	public void setObject(ObjectItem object) { this.object = object; };
	public int getOrigin() { return this.origin; }
	public void setOrigin(int origin) { this.origin = origin; };

	public ObjectAddedMessage(){
	}

	public ObjectAddedMessage(ObjectItem object, int origin){
		this.object = object;
		this.origin = origin;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			object.Serialize(writer);
			writer.writeByte(this.origin);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.object = new ObjectItem();
			this.object.Deserialize(reader);
			this.origin = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
