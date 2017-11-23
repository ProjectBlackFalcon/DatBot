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
import protocol.network.types.game.data.items.ObjectItemToSell;

@SuppressWarnings("unused")
public class ObjectItemToSellInBid extends ObjectItemToSell {
	public static final int ProtocolId = 164;

	public int unsoldDelay;

	public ObjectItemToSellInBid(){
	}

	public ObjectItemToSellInBid(int unsoldDelay){
		this.unsoldDelay = unsoldDelay;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.unsoldDelay);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.unsoldDelay = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("unsoldDelay : " + this.unsoldDelay);
	//}
}
