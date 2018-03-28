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
import protocol.network.types.game.data.items.ObjectItemGenericQuantityPrice;

@SuppressWarnings("unused")
public class ExchangeOfflineSoldItemsMessage extends NetworkMessage {
	public static final int ProtocolId = 6613;

	private List<ObjectItemGenericQuantityPrice> bidHouseItems;
	private List<ObjectItemGenericQuantityPrice> merchantItems;

	public List<ObjectItemGenericQuantityPrice> getBidHouseItems() { return this.bidHouseItems; }
	public void setBidHouseItems(List<ObjectItemGenericQuantityPrice> bidHouseItems) { this.bidHouseItems = bidHouseItems; };
	public List<ObjectItemGenericQuantityPrice> getMerchantItems() { return this.merchantItems; }
	public void setMerchantItems(List<ObjectItemGenericQuantityPrice> merchantItems) { this.merchantItems = merchantItems; };

	public ExchangeOfflineSoldItemsMessage(){
	}

	public ExchangeOfflineSoldItemsMessage(List<ObjectItemGenericQuantityPrice> bidHouseItems, List<ObjectItemGenericQuantityPrice> merchantItems){
		this.bidHouseItems = bidHouseItems;
		this.merchantItems = merchantItems;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.bidHouseItems.size());
			int _loc2_ = 0;
			while( _loc2_ < this.bidHouseItems.size()){
				this.bidHouseItems.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.merchantItems.size());
			int _loc3_ = 0;
			while( _loc3_ < this.merchantItems.size()){
				this.merchantItems.get(_loc3_).Serialize(writer);
				_loc3_++;
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
			this.bidHouseItems = new ArrayList<ObjectItemGenericQuantityPrice>();
			while( _loc3_ <  _loc2_){
				ObjectItemGenericQuantityPrice _loc15_ = new ObjectItemGenericQuantityPrice();
				_loc15_.Deserialize(reader);
				this.bidHouseItems.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.merchantItems = new ArrayList<ObjectItemGenericQuantityPrice>();
			while( _loc5_ <  _loc4_){
				ObjectItemGenericQuantityPrice _loc16_ = new ObjectItemGenericQuantityPrice();
				_loc16_.Deserialize(reader);
				this.merchantItems.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
