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
public class ExchangeStartedTaxCollectorShopMessage extends NetworkMessage {
	public static final int ProtocolId = 6664;

	public List<ObjectItem> objects;
	public long kamas;

	public ExchangeStartedTaxCollectorShopMessage(){
	}

	public ExchangeStartedTaxCollectorShopMessage(List<ObjectItem> objects, long kamas){
		this.objects = objects;
		this.kamas = kamas;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objects.size()){
				this.objects.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeVarLong(this.kamas);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objects = new ArrayList<ObjectItem>();
			while( _loc3_ <  _loc2_){
				ObjectItem _loc15_ = new ObjectItem();
				_loc15_.Deserialize(reader);
				this.objects.add(_loc15_);
				_loc3_++;
			}
			this.kamas = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(ObjectItem a : objects) {
			//Network.appendDebug("objects : " + a);
		//}
		//Network.appendDebug("kamas : " + this.kamas);
	//}
}
