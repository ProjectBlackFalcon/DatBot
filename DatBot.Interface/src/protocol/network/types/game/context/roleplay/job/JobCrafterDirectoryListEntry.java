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
import protocol.network.types.game.context.roleplay.job.JobCrafterDirectoryEntryPlayerInfo;
import protocol.network.types.game.context.roleplay.job.JobCrafterDirectoryEntryJobInfo;

@SuppressWarnings("unused")
public class JobCrafterDirectoryListEntry extends NetworkMessage {
	public static final int ProtocolId = 196;

	private JobCrafterDirectoryEntryPlayerInfo playerInfo;
	private JobCrafterDirectoryEntryJobInfo jobInfo;

	public JobCrafterDirectoryEntryPlayerInfo getPlayerInfo() { return this.playerInfo; }
	public void setPlayerInfo(JobCrafterDirectoryEntryPlayerInfo playerInfo) { this.playerInfo = playerInfo; };
	public JobCrafterDirectoryEntryJobInfo getJobInfo() { return this.jobInfo; }
	public void setJobInfo(JobCrafterDirectoryEntryJobInfo jobInfo) { this.jobInfo = jobInfo; };

	public JobCrafterDirectoryListEntry(){
	}

	public JobCrafterDirectoryListEntry(JobCrafterDirectoryEntryPlayerInfo playerInfo, JobCrafterDirectoryEntryJobInfo jobInfo){
		this.playerInfo = playerInfo;
		this.jobInfo = jobInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			playerInfo.Serialize(writer);
			jobInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerInfo = new JobCrafterDirectoryEntryPlayerInfo();
			this.playerInfo.Deserialize(reader);
			this.jobInfo = new JobCrafterDirectoryEntryJobInfo();
			this.jobInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
