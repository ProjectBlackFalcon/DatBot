package protocol.network.messages.game.inventory.exchanges;

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
import protocol.network.types.game.data.items.ObjectItemToSellInBid;

@SuppressWarnings("unused")
public class ExchangeBidHouseItemAddOkMessage extends NetworkMessage {
	public static final int ProtocolId = 5945;

	public ObjectItemToSellInBid itemInfo;

	public ExchangeBidHouseItemAddOkMessage(){
	}

	public ExchangeBidHouseItemAddOkMessage(ObjectItemToSellInBid itemInfo){
		this.itemInfo = itemInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			itemInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.itemInfo = new ObjectItemToSellInBid();
			this.itemInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("itemInfo : " + this.itemInfo);
	//}
}