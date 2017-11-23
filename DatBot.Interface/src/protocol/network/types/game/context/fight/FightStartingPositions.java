package protocol.network.types.game.context.fight;

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
public class FightStartingPositions extends NetworkMessage {
	public static final int ProtocolId = 513;

	public List<Integer> positionsForChallengers;
	public List<Integer> positionsForDefenders;

	public FightStartingPositions(){
	}

	public FightStartingPositions(List<Integer> positionsForChallengers, List<Integer> positionsForDefenders){
		this.positionsForChallengers = positionsForChallengers;
		this.positionsForDefenders = positionsForDefenders;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.positionsForChallengers.size());
			int _loc2_ = 0;
			while( _loc2_ < this.positionsForChallengers.size()){
				writer.writeVarShort(this.positionsForChallengers.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.positionsForDefenders.size());
			int _loc3_ = 0;
			while( _loc3_ < this.positionsForDefenders.size()){
				writer.writeVarShort(this.positionsForDefenders.get(_loc3_));
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
			this.positionsForChallengers = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.positionsForChallengers.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.positionsForDefenders = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.positionsForDefenders.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : positionsForChallengers) {
			//Network.appendDebug("positionsForChallengers : " + a);
		//}
		//for(Integer a : positionsForDefenders) {
			//Network.appendDebug("positionsForDefenders : " + a);
		//}
	//}
}
