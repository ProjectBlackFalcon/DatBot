package protocol.network.types.game.achievement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.achievement.AchievementAchieved;

@SuppressWarnings("unused")
public class AchievementAchievedRewardable extends AchievementAchieved {
	public static final int ProtocolId = 515;

	private int finishedlevel;

	public int getFinishedlevel() { return this.finishedlevel; }
	public void setFinishedlevel(int finishedlevel) { this.finishedlevel = finishedlevel; };

	public AchievementAchievedRewardable(){
	}

	public AchievementAchievedRewardable(int finishedlevel){
		this.finishedlevel = finishedlevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.finishedlevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.finishedlevel = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
