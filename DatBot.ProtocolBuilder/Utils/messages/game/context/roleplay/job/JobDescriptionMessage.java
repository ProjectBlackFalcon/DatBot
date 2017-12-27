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
public class JobDescriptionMessage extends NetworkMessage {
	public static final int ProtocolId = 5655;

	private List<JobDescription> jobsDescription;

	public List<JobDescription> getJobsDescription() { return this.jobsDescription; };
	public void setJobsDescription(List<JobDescription> jobsDescription) { this.jobsDescription = jobsDescription; };

	public JobDescriptionMessage(){
	}

	public JobDescriptionMessage(List<JobDescription> jobsDescription){
		this.jobsDescription = jobsDescription;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.jobsDescription.size());
			int _loc2_ = 0;
			while( _loc2_ < this.jobsDescription.size()){
				this.jobsDescription.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.jobsDescription = new ArrayList<JobDescription>();
			while( _loc3_ <  _loc2_){
				JobDescription _loc15_ = new JobDescription();
				_loc15_.Deserialize(reader);
				this.jobsDescription.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
