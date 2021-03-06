package protocol.network.types.game.context;

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
public class EntityMovementInformations extends NetworkMessage {
	public static final int ProtocolId = 63;

	private int id;
	private List<Integer> steps;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public List<Integer> getSteps() { return this.steps; }
	public void setSteps(List<Integer> steps) { this.steps = steps; };

	public EntityMovementInformations(){
	}

	public EntityMovementInformations(int id, List<Integer> steps){
		this.id = id;
		this.steps = steps;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.id);
			writer.writeShort(this.steps.size());
			int _loc2_ = 0;
			while( _loc2_ < this.steps.size()){
				writer.writeByte(this.steps.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.steps = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.steps.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
