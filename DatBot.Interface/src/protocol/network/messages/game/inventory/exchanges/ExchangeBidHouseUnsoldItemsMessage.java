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
import protocol.network.types.game.data.items.ObjectItemGenericQuantity;

@SuppressWarnings("unused")
public class ExchangeBidHouseUnsoldItemsMessage extends NetworkMessage {
	public static final int ProtocolId = 6612;

	public List<ObjectItemGenericQuantity> items;

	public ExchangeBidHouseUnsoldItemsMessage(){
	}

	public ExchangeBidHouseUnsoldItemsMessage(List<ObjectItemGenericQuantity> items){
		this.items = items;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.items.size());
			int _loc2_ = 0;
			while( _loc2_ < this.items.size()){
				this.items.get(_loc2_).Serialize(writer);
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
			this.items = new ArrayList<ObjectItemGenericQuantity>();
			while( _loc3_ <  _loc2_){
				ObjectItemGenericQuantity _loc15_ = new ObjectItemGenericQuantity();
				_loc15_.Deserialize(reader);
				this.items.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(ObjectItemGenericQuantity a : items) {
			//Network.appendDebug("items : " + a);
		//}
	//}
}
