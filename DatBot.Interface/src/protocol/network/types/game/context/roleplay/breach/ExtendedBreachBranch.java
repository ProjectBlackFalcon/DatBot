package protocol.network.types.game.context.roleplay.breach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.breach.BreachBranch;
import protocol.network.types.game.context.roleplay.MonsterInGroupLightInformations;
import protocol.network.types.game.context.roleplay.breach.BreachReward;

@SuppressWarnings("unused")
public class ExtendedBreachBranch extends BreachBranch {
	public static final int ProtocolId = 560;

	private List<MonsterInGroupLightInformations> monsters;
	private List<BreachReward> rewards;
	private int modifier;
	private int prize;

	public List<MonsterInGroupLightInformations> getMonsters() { return this.monsters; }
	public void setMonsters(List<MonsterInGroupLightInformations> monsters) { this.monsters = monsters; };
	public List<BreachReward> getRewards() { return this.rewards; }
	public void setRewards(List<BreachReward> rewards) { this.rewards = rewards; };
	public int getModifier() { return this.modifier; }
	public void setModifier(int modifier) { this.modifier = modifier; };
	public int getPrize() { return this.prize; }
	public void setPrize(int prize) { this.prize = prize; };

	public ExtendedBreachBranch(){
	}

	public ExtendedBreachBranch(List<MonsterInGroupLightInformations> monsters, List<BreachReward> rewards, int modifier, int prize){
		this.monsters = monsters;
		this.rewards = rewards;
		this.modifier = modifier;
		this.prize = prize;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.monsters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.monsters.size()){
				this.monsters.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.rewards.size());
			int _loc3_ = 0;
			while( _loc3_ < this.rewards.size()){
				this.rewards.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
			writer.writeVarInt(this.modifier);
			writer.writeVarInt(this.prize);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.monsters = new ArrayList<MonsterInGroupLightInformations>();
			while( _loc3_ <  _loc2_){
				MonsterInGroupLightInformations _loc15_ = new MonsterInGroupLightInformations();
				_loc15_.Deserialize(reader);
				this.monsters.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.rewards = new ArrayList<BreachReward>();
			while( _loc5_ <  _loc4_){
				BreachReward _loc16_ = new BreachReward();
				_loc16_.Deserialize(reader);
				this.rewards.add(_loc16_);
				_loc5_++;
			}
			this.modifier = reader.readVarInt();
			this.prize = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
