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
public class ExchangeShopStockStartedMessage extends NetworkMessage {
	public static final int ProtocolId = 5910;

	public List<ObjectItemToSell> objectsInfos;

	public ExchangeShopStockStartedMessage(){
	}

	public ExchangeShopStockStartedMessage(List<ObjectItemToSell> objectsInfos){
		this.objectsInfos = objectsInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objectsInfos.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectsInfos.size()){
				this.objectsInfos.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objectsInfos = new ArrayList<ObjectItemToSell>();
			while( _loc3_ <  _loc2_){
				ObjectItemToSell _loc15_ = new ObjectItemToSell();
				_loc15_.Deserialize(reader);
				this.objectsInfos.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(ObjectItemToSell a : objectsInfos) {
			//Network.appendDebug("objectsInfos : " + a);
		//}
	//}
}
