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
public class ObjectModifiedMessage extends NetworkMessage {
	public static final int ProtocolId = 3029;

	public ObjectItem object;

	public ObjectModifiedMessage(){
	}

	public ObjectModifiedMessage(ObjectItem object){
		this.object = object;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			object.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.object = new ObjectItem();
			this.object.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("object : " + this.object);
	//}
}
