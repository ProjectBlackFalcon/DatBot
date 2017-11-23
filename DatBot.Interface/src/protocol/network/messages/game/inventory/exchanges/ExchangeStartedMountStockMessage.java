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
import protocol.network.types.game.data.items.ObjectItem;

@SuppressWarnings("unused")
public class ExchangeStartedMountStockMessage extends NetworkMessage {
	public static final int ProtocolId = 5984;

	public List<ObjectItem> objectsInfos;

	public ExchangeStartedMountStockMessage(){
	}

	public ExchangeStartedMountStockMessage(List<ObjectItem> objectsInfos){
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
			this.objectsInfos = new ArrayList<ObjectItem>();
			while( _loc3_ <  _loc2_){
				ObjectItem _loc15_ = new ObjectItem();
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
		//for(ObjectItem a : objectsInfos) {
			//Network.appendDebug("objectsInfos : " + a);
		//}
	//}
}
