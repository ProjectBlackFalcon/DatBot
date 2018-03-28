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

@SuppressWarnings("unused")
public class AchievementRewardErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 6375;

	private int achievementId;

	public int getAchievementId() { return this.achievementId; }
	public void setAchievementId(int achievementId) { this.achievementId = achievementId; };

	public AchievementRewardErrorMessage(){
	}

	public AchievementRewardErrorMessage(int achievementId){
		this.achievementId = achievementId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.achievementId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.achievementId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
