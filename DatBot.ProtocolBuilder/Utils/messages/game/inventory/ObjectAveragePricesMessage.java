package protocol.network.messages.game.inventory;

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
public class ObjectAveragePricesMessage extends NetworkMessage {
	public static final int ProtocolId = 6335;

	private List<Integer> ids;
	private List<Long> avgPrices;

	public List<Integer> getIds() { return this.ids; }
	public void setIds(List<Integer> ids) { this.ids = ids; };
	public List<Long> getAvgPrices() { return this.avgPrices; }
	public void setAvgPrices(List<Long> avgPrices) { this.avgPrices = avgPrices; };

	public ObjectAveragePricesMessage(){
	}

	public ObjectAveragePricesMessage(List<Integer> ids, List<Long> avgPrices){
		this.ids = ids;
		this.avgPrices = avgPrices;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.ids.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ids.size()){
				writer.writeVarShort(this.ids.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.avgPrices.size());
			int _loc3_ = 0;
			while( _loc3_ < this.avgPrices.size()){
				writer.writeVarLong(this.avgPrices.get(_loc3_));
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
			this.ids = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.ids.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.avgPrices = new ArrayList<Long>();
			while( _loc5_ <  _loc4_){
				long _loc16_ = reader.readVarLong();
				this.avgPrices.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
