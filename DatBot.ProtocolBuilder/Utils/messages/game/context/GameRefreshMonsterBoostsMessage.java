package protocol.network.messages.game.context;

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
import protocol.network.types.game.context.roleplay.MonsterBoosts;

@SuppressWarnings("unused")
public class GameRefreshMonsterBoostsMessage extends NetworkMessage {
	public static final int ProtocolId = 6618;

	private List<MonsterBoosts> monsterBoosts;
	private List<MonsterBoosts> familyBoosts;

	public List<MonsterBoosts> getMonsterBoosts() { return this.monsterBoosts; }
	public void setMonsterBoosts(List<MonsterBoosts> monsterBoosts) { this.monsterBoosts = monsterBoosts; };
	public List<MonsterBoosts> getFamilyBoosts() { return this.familyBoosts; }
	public void setFamilyBoosts(List<MonsterBoosts> familyBoosts) { this.familyBoosts = familyBoosts; };

	public GameRefreshMonsterBoostsMessage(){
	}

	public GameRefreshMonsterBoostsMessage(List<MonsterBoosts> monsterBoosts, List<MonsterBoosts> familyBoosts){
		this.monsterBoosts = monsterBoosts;
		this.familyBoosts = familyBoosts;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.monsterBoosts.size());
			int _loc2_ = 0;
			while( _loc2_ < this.monsterBoosts.size()){
				this.monsterBoosts.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.familyBoosts.size());
			int _loc3_ = 0;
			while( _loc3_ < this.familyBoosts.size()){
				this.familyBoosts.get(_loc3_).Serialize(writer);
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
			this.monsterBoosts = new ArrayList<MonsterBoosts>();
			while( _loc3_ <  _loc2_){
				MonsterBoosts _loc15_ = new MonsterBoosts();
				_loc15_.Deserialize(reader);
				this.monsterBoosts.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.familyBoosts = new ArrayList<MonsterBoosts>();
			while( _loc5_ <  _loc4_){
				MonsterBoosts _loc16_ = new MonsterBoosts();
				_loc16_.Deserialize(reader);
				this.familyBoosts.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
