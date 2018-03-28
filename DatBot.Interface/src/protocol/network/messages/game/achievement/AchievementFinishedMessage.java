package protocol.network.messages.game.achievement;

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
import protocol.network.types.game.achievement.AchievementAchievedRewardable;

@SuppressWarnings("unused")
public class AchievementFinishedMessage extends NetworkMessage {
	public static final int ProtocolId = 6208;

	private AchievementAchievedRewardable achievement;

	public AchievementAchievedRewardable getAchievement() { return this.achievement; }
	public void setAchievement(AchievementAchievedRewardable achievement) { this.achievement = achievement; };

	public AchievementFinishedMessage(){
	}

	public AchievementFinishedMessage(AchievementAchievedRewardable achievement){
		this.achievement = achievement;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			achievement.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.achievement = new AchievementAchievedRewardable();
			this.achievement.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
