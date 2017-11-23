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
public class ObtainedItemMessage extends NetworkMessage {
	public static final int ProtocolId = 6519;

	public int genericId;
	public int baseQuantity;

	public ObtainedItemMessage(){
	}

	public ObtainedItemMessage(int genericId, int baseQuantity){
		this.genericId = genericId;
		this.baseQuantity = baseQuantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.genericId);
			writer.writeVarInt(this.baseQuantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.genericId = reader.readVarShort();
			this.baseQuantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("genericId : " + this.genericId);
		//Network.appendDebug("baseQuantity : " + this.baseQuantity);
	//}
}
