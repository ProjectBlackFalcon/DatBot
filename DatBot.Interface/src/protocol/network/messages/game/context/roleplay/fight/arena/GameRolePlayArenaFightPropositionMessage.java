package protocol.network.messages.game.context.roleplay.fight.arena;

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
public class GameRolePlayArenaFightPropositionMessage extends NetworkMessage {
	public static final int ProtocolId = 6276;

	public int fightId;
	public List<Double> alliesId;
	public int duration;

	public GameRolePlayArenaFightPropositionMessage(){
	}

	public GameRolePlayArenaFightPropositionMessage(int fightId, List<Double> alliesId, int duration){
		this.fightId = fightId;
		this.alliesId = alliesId;
		this.duration = duration;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.fightId);
			writer.writeShort(this.alliesId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.alliesId.size()){
				writer.writeDouble(this.alliesId.get(_loc2_));
				_loc2_++;
			}
			writer.writeVarShort(this.duration);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.alliesId = new ArrayList<Double>();
			while( _loc3_ <  _loc2_){
				double _loc15_ = reader.readDouble();
				this.alliesId.add(_loc15_);
				_loc3_++;
			}
			this.duration = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//for(Double a : alliesId) {
			//Network.appendDebug("alliesId : " + a);
		//}
		//Network.appendDebug("duration : " + this.duration);
	//}
}