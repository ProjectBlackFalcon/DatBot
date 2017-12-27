package protocol.network.messages.game.context.roleplay.job;

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
import protocol.network.types.game.context.roleplay.job.JobDescription;

@SuppressWarnings("unused")
public class JobLevelUpMessage extends NetworkMessage {
	public static final int ProtocolId = 5656;

	private int newLevel;
	private JobDescription jobsDescription;

	public int getNewLevel() { return this.newLevel; };
	public void setNewLevel(int newLevel) { this.newLevel = newLevel; };
	public JobDescription getJobsDescription() { return this.jobsDescription; };
	public void setJobsDescription(JobDescription jobsDescription) { this.jobsDescription = jobsDescription; };

	public JobLevelUpMessage(){
	}

	public JobLevelUpMessage(int newLevel, JobDescription jobsDescription){
		this.newLevel = newLevel;
		this.jobsDescription = jobsDescription;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.newLevel);
			jobsDescription.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.newLevel = reader.readByte();
			this.jobsDescription = new JobDescription();
			this.jobsDescription.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
