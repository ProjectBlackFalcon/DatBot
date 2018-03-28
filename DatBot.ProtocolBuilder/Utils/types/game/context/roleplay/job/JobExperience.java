package protocol.network.types.game.context.roleplay.job;

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
public class JobExperience extends NetworkMessage {
	public static final int ProtocolId = 98;

	private int jobId;
	private int jobLevel;
	private long jobXP;
	private long jobXpLevelFloor;
	private long jobXpNextLevelFloor;

	public int getJobId() { return this.jobId; }
	public void setJobId(int jobId) { this.jobId = jobId; };
	public int getJobLevel() { return this.jobLevel; }
	public void setJobLevel(int jobLevel) { this.jobLevel = jobLevel; };
	public long getJobXP() { return this.jobXP; }
	public void setJobXP(long jobXP) { this.jobXP = jobXP; };
	public long getJobXpLevelFloor() { return this.jobXpLevelFloor; }
	public void setJobXpLevelFloor(long jobXpLevelFloor) { this.jobXpLevelFloor = jobXpLevelFloor; };
	public long getJobXpNextLevelFloor() { return this.jobXpNextLevelFloor; }
	public void setJobXpNextLevelFloor(long jobXpNextLevelFloor) { this.jobXpNextLevelFloor = jobXpNextLevelFloor; };

	public JobExperience(){
	}

	public JobExperience(int jobId, int jobLevel, long jobXP, long jobXpLevelFloor, long jobXpNextLevelFloor){
		this.jobId = jobId;
		this.jobLevel = jobLevel;
		this.jobXP = jobXP;
		this.jobXpLevelFloor = jobXpLevelFloor;
		this.jobXpNextLevelFloor = jobXpNextLevelFloor;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.jobId);
			writer.writeByte(this.jobLevel);
			writer.writeVarLong(this.jobXP);
			writer.writeVarLong(this.jobXpLevelFloor);
			writer.writeVarLong(this.jobXpNextLevelFloor);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.jobId = reader.readByte();
			this.jobLevel = reader.readByte();
			this.jobXP = reader.readVarLong();
			this.jobXpLevelFloor = reader.readVarLong();
			this.jobXpNextLevelFloor = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
