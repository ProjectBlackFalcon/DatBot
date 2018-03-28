package protocol.network.messages.game.context.roleplay.paddock;

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
public class GameDataPlayFarmObjectAnimationMessage extends NetworkMessage {
	public static final int ProtocolId = 6026;

	private List<Integer> cellId;

	public List<Integer> getCellId() { return this.cellId; }
	public void setCellId(List<Integer> cellId) { this.cellId = cellId; };

	public GameDataPlayFarmObjectAnimationMessage(){
	}

	public GameDataPlayFarmObjectAnimationMessage(List<Integer> cellId){
		this.cellId = cellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.cellId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.cellId.size()){
				writer.writeVarShort(this.cellId.get(_loc2_));
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
			this.cellId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.cellId.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
