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
import protocol.network.types.game.data.items.ObjectItemToSell;

@SuppressWarnings("unused")
public class ExchangeShopStockMovementUpdatedMessage extends NetworkMessage {
	public static final int ProtocolId = 5909;

	public ObjectItemToSell objectInfo;

	public ExchangeShopStockMovementUpdatedMessage(){
	}

	public ExchangeShopStockMovementUpdatedMessage(ObjectItemToSell objectInfo){
		this.objectInfo = objectInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			objectInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectInfo = new ObjectItemToSell();
			this.objectInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("objectInfo : " + this.objectInfo);
	//}
}
