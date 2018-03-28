package protocol.network.messages.game.context.fight;

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
public class GameFightTurnListMessage extends NetworkMessage {
	public static final int ProtocolId = 713;

	private List<Double> ids;
	private List<Double> deadsIds;

	public List<Double> getIds() { return this.ids; }
	public void setIds(List<Double> ids) { this.ids = ids; };
	public List<Double> getDeadsIds() { return this.deadsIds; }
	public void setDeadsIds(List<Double> deadsIds) { this.deadsIds = deadsIds; };

	public GameFightTurnListMessage(){
	}

	public GameFightTurnListMessage(List<Double> ids, List<Double> deadsIds){
		this.ids = ids;
		this.deadsIds = deadsIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.ids.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ids.size()){
				writer.writeDouble(this.ids.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.deadsIds.size());
			int _loc3_ = 0;
			while( _loc3_ < this.deadsIds.size()){
				writer.writeDouble(this.deadsIds.get(_loc3_));
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
			this.ids = new ArrayList<Double>();
			while( _loc3_ <  _loc2_){
				double _loc15_ = reader.readDouble();
				this.ids.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.deadsIds = new ArrayList<Double>();
			while( _loc5_ <  _loc4_){
				double _loc16_ = reader.readDouble();
				this.deadsIds.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
