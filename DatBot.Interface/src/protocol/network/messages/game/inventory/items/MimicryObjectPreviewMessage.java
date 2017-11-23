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
public class MimicryObjectPreviewMessage extends NetworkMessage {
	public static final int ProtocolId = 6458;

	public ObjectItem result;

	public MimicryObjectPreviewMessage(){
	}

	public MimicryObjectPreviewMessage(ObjectItem result){
		this.result = result;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			result.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.result = new ObjectItem();
			this.result.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("result : " + this.result);
	//}
}
