package protocol.network.types.game.dare;

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
public class DareCriteria extends NetworkMessage {
	public static final int ProtocolId = 501;

	private int type;
	private List<Integer> params;

	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };
	public List<Integer> getParams() { return this.params; }
	public void setParams(List<Integer> params) { this.params = params; };

	public DareCriteria(){
	}

	public DareCriteria(int type, List<Integer> params){
		this.type = type;
		this.params = params;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.type);
			writer.writeShort(this.params.size());
			int _loc2_ = 0;
			while( _loc2_ < this.params.size()){
				writer.writeInt(this.params.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.params = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.params.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
