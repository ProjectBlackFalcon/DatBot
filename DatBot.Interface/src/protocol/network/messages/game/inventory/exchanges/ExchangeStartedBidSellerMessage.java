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
import protocol.network.types.game.data.items.SellerBuyerDescriptor;
import protocol.network.types.game.data.items.ObjectItemToSellInBid;

@SuppressWarnings("unused")
public class ExchangeStartedBidSellerMessage extends NetworkMessage {
	public static final int ProtocolId = 5905;

	public SellerBuyerDescriptor sellerDescriptor;
	public List<ObjectItemToSellInBid> objectsInfos;

	public ExchangeStartedBidSellerMessage(){
	}

	public ExchangeStartedBidSellerMessage(SellerBuyerDescriptor sellerDescriptor, List<ObjectItemToSellInBid> objectsInfos){
		this.sellerDescriptor = sellerDescriptor;
		this.objectsInfos = objectsInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			sellerDescriptor.Serialize(writer);
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
			this.sellerDescriptor = new SellerBuyerDescriptor();
			this.sellerDescriptor.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objectsInfos = new ArrayList<ObjectItemToSellInBid>();
			while( _loc3_ <  _loc2_){
				ObjectItemToSellInBid _loc15_ = new ObjectItemToSellInBid();
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
		//Network.appendDebug("sellerDescriptor : " + this.sellerDescriptor);
		//for(ObjectItemToSellInBid a : objectsInfos) {
			//Network.appendDebug("objectsInfos : " + a);
		//}
	//}
}
