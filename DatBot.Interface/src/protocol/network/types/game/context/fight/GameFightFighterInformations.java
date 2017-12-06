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
import protocol.network.types.game.context.GameContextActorInformations;
import protocol.network.types.game.context.fight.GameFightMinimalStats;

@SuppressWarnings("unused")
public class GameFightFighterInformations extends GameContextActorInformations {
	public static final int ProtocolId = 143;

	public int teamId;
	public int wave;
	public boolean alive;
	public GameFightMinimalStats stats;
	public List<Integer> previousPositions;

	public GameFightFighterInformations(){
	}

	public GameFightFighterInformations(int teamId, int wave, boolean alive, GameFightMinimalStats stats, List<Integer> previousPositions){
		this.teamId = teamId;
		this.wave = wave;
		this.alive = alive;
		this.stats = stats;
		this.previousPositions = previousPositions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.teamId);
			writer.writeByte(this.wave);
			writer.writeBoolean(this.alive);
			writer.writeShort(GameFightMinimalStats.ProtocolId);
			writer.writeShort(this.previousPositions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.previousPositions.size()){
				writer.writeVarShort(this.previousPositions.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.teamId = reader.readByte();
			this.wave = reader.readByte();
			this.alive = reader.readBoolean();
			this.stats = (GameFightMinimalStats) ProtocolTypeManager.getInstance(reader.readShort());
			this.stats.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.previousPositions = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.previousPositions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("teamId : " + this.teamId);
		//Network.appendDebug("wave : " + this.wave);
		//Network.appendDebug("alive : " + this.alive);
		//Network.appendDebug("stats : " + this.stats);
		//for(Integer a : previousPositions) {
			//Network.appendDebug("previousPositions : " + a);
		//}
	//}
}
