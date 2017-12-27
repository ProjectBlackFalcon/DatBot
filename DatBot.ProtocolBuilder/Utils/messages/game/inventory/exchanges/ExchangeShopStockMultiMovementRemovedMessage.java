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

@SuppressWarnings("unused")
public class ExchangeShopStockMultiMovementRemovedMessage extends NetworkMessage {
	public static final int ProtocolId = 6037;

	private List<Integer> objectIdList;

	public List<Integer> getObjectIdList() { return this.objectIdList; };
	public void setObjectIdList(List<Integer> objectIdList) { this.objectIdList = objectIdList; };

	public ExchangeShopStockMultiMovementRemovedMessage(){
	}

	public ExchangeShopStockMultiMovementRemovedMessage(List<Integer> objectIdList){
		this.objectIdList = objectIdList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objectIdList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectIdList.size()){
				writer.writeVarInt(this.objectIdList.get(_loc2_));
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
			this.objectIdList = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.objectIdList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
