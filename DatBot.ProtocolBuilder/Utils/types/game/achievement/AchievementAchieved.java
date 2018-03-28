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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class AchievementAchieved extends NetworkMessage {
	public static final int ProtocolId = 514;

	private int id;
	private long achievedBy;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public long getAchievedBy() { return this.achievedBy; }
	public void setAchievedBy(long achievedBy) { this.achievedBy = achievedBy; };

	public AchievementAchieved(){
	}

	public AchievementAchieved(int id, long achievedBy){
		this.id = id;
		this.achievedBy = achievedBy;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeVarLong(this.achievedBy);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			this.achievedBy = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
