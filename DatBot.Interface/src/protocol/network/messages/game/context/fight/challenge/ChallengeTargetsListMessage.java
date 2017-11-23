package protocol.network.messages.game.context.fight.challenge;

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
public class ChallengeTargetsListMessage extends NetworkMessage {
	public static final int ProtocolId = 5613;

	public List<Double> targetIds;
	public List<Integer> targetCells;

	public ChallengeTargetsListMessage(){
	}

	public ChallengeTargetsListMessage(List<Double> targetIds, List<Integer> targetCells){
		this.targetIds = targetIds;
		this.targetCells = targetCells;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.targetIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.targetIds.size()){
				writer.writeDouble(this.targetIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.targetCells.size());
			int _loc3_ = 0;
			while( _loc3_ < this.targetCells.size()){
				writer.writeShort(this.targetCells.get(_loc3_));
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
			this.targetIds = new ArrayList<Double>();
			while( _loc3_ <  _loc2_){
				double _loc15_ = reader.readDouble();
				this.targetIds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.targetCells = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readShort();
				this.targetCells.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Double a : targetIds) {
			//Network.appendDebug("targetIds : " + a);
		//}
		//for(Integer a : targetCells) {
			//Network.appendDebug("targetCells : " + a);
		//}
	//}
}
