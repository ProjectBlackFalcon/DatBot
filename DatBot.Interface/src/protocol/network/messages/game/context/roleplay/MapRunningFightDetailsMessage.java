package protocol.network.messages.game.context.roleplay;

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
import protocol.network.types.game.context.fight.GameFightFighterLightInformations;

@SuppressWarnings("unused")
public class MapRunningFightDetailsMessage extends NetworkMessage {
	public static final int ProtocolId = 5751;

	public int fightId;
	public List<GameFightFighterLightInformations> attackers;
	public List<GameFightFighterLightInformations> defenders;

	public MapRunningFightDetailsMessage(){
	}

	public MapRunningFightDetailsMessage(int fightId, List<GameFightFighterLightInformations> attackers, List<GameFightFighterLightInformations> defenders){
		this.fightId = fightId;
		this.attackers = attackers;
		this.defenders = defenders;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.fightId);
			writer.writeShort(this.attackers.size());
			int _loc2_ = 0;
			while( _loc2_ < this.attackers.size()){
				writer.writeShort(GameFightFighterLightInformations.ProtocolId);
				this.attackers.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.defenders.size());
			int _loc3_ = 0;
			while( _loc3_ < this.defenders.size()){
				writer.writeShort(GameFightFighterLightInformations.ProtocolId);
				this.defenders.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
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
			this.attackers = new ArrayList<GameFightFighterLightInformations>();
			while( _loc3_ <  _loc2_){
				GameFightFighterLightInformations _loc15_ = (GameFightFighterLightInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.attackers.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.defenders = new ArrayList<GameFightFighterLightInformations>();
			while( _loc5_ <  _loc4_){
				GameFightFighterLightInformations _loc16_ = (GameFightFighterLightInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc16_.Deserialize(reader);
				this.defenders.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		append();
	}

	private void append(){
		Network.appendDebug("fightId : " + this.fightId);
		for(GameFightFighterLightInformations a : attackers) {
			Network.appendDebug("\tid : " + a.id);
			Network.appendDebug("\tbreed : " + a.breed);
			Network.appendDebug("\tlevel : " + a.level);
			Network.appendDebug("\twave : " + a.wave);
			Network.appendDebug("\talive : " + a.alive);
			Network.appendDebug("\tsex : " + a.sex);
		}
		for(GameFightFighterLightInformations a : defenders) {
			Network.appendDebug("\tid : " + a.id);
			Network.appendDebug("\tbreed : " + a.breed);
			Network.appendDebug("\tlevel : " + a.level);
			Network.appendDebug("\twave : " + a.wave);
			Network.appendDebug("\talive : " + a.alive);
			Network.appendDebug("\tsex : " + a.sex);
		}
	}
}