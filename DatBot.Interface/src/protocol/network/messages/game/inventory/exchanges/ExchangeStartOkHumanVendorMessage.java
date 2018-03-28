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
import protocol.network.types.game.data.items.ObjectItemToSellInHumanVendorShop;

@SuppressWarnings("unused")
public class ExchangeStartOkHumanVendorMessage extends NetworkMessage {
	public static final int ProtocolId = 5767;

	private double sellerId;
	private List<ObjectItemToSellInHumanVendorShop> objectsInfos;

	public double getSellerId() { return this.sellerId; }
	public void setSellerId(double sellerId) { this.sellerId = sellerId; };
	public List<ObjectItemToSellInHumanVendorShop> getObjectsInfos() { return this.objectsInfos; }
	public void setObjectsInfos(List<ObjectItemToSellInHumanVendorShop> objectsInfos) { this.objectsInfos = objectsInfos; };

	public ExchangeStartOkHumanVendorMessage(){
	}

	public ExchangeStartOkHumanVendorMessage(double sellerId, List<ObjectItemToSellInHumanVendorShop> objectsInfos){
		this.sellerId = sellerId;
		this.objectsInfos = objectsInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.sellerId);
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
			this.sellerId = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objectsInfos = new ArrayList<ObjectItemToSellInHumanVendorShop>();
			while( _loc3_ <  _loc2_){
				ObjectItemToSellInHumanVendorShop _loc15_ = new ObjectItemToSellInHumanVendorShop();
				_loc15_.Deserialize(reader);
				this.objectsInfos.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
