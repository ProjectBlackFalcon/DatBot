package protocol.network.messages.game.dare;

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
import protocol.network.types.game.dare.DareReward;

@SuppressWarnings("unused")
public class DareRewardWonMessage extends NetworkMessage {
	public static final int ProtocolId = 6678;

	private DareReward reward;

	public DareReward getReward() { return this.reward; }
	public void setReward(DareReward reward) { this.reward = reward; };

	public DareRewardWonMessage(){
	}

	public DareRewardWonMessage(DareReward reward){
		this.reward = reward;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			reward.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.reward = new DareReward();
			this.reward.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
