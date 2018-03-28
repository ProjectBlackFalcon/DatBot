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
import protocol.network.types.game.achievement.Achievement;

@SuppressWarnings("unused")
public class AchievementDetailsMessage extends NetworkMessage {
	public static final int ProtocolId = 6378;

	private Achievement achievement;

	public Achievement getAchievement() { return this.achievement; }
	public void setAchievement(Achievement achievement) { this.achievement = achievement; };

	public AchievementDetailsMessage(){
	}

	public AchievementDetailsMessage(Achievement achievement){
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
			this.achievement = new Achievement();
			this.achievement.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
