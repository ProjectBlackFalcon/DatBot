package protocol.network.messages.game.context.roleplay.breach.reward;

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
import protocol.network.types.game.context.roleplay.breach.BreachReward;

@SuppressWarnings("unused")
public class BreachRewardsMessage extends NetworkMessage {
	public static final int ProtocolId = 6813;

	private List<BreachReward> rewards;
	private BreachReward save;

	public List<BreachReward> getRewards() { return this.rewards; }
	public void setRewards(List<BreachReward> rewards) { this.rewards = rewards; };
	public BreachReward getSave() { return this.save; }
	public void setSave(BreachReward save) { this.save = save; };

	public BreachRewardsMessage(){
	}

	public BreachRewardsMessage(List<BreachReward> rewards, BreachReward save){
		this.rewards = rewards;
		this.save = save;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.rewards.size());
			int _loc2_ = 0;
			while( _loc2_ < this.rewards.size()){
				this.rewards.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			save.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.rewards = new ArrayList<BreachReward>();
			while( _loc3_ <  _loc2_){
				BreachReward _loc15_ = new BreachReward();
				_loc15_.Deserialize(reader);
				this.rewards.add(_loc15_);
				_loc3_++;
			}
			this.save = new BreachReward();
			this.save.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
