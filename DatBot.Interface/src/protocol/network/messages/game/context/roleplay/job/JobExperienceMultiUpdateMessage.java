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
import protocol.network.types.game.context.roleplay.job.JobExperience;

@SuppressWarnings("unused")
public class JobExperienceMultiUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5809;

	private List<JobExperience> experiencesUpdate;

	public List<JobExperience> getExperiencesUpdate() { return this.experiencesUpdate; }
	public void setExperiencesUpdate(List<JobExperience> experiencesUpdate) { this.experiencesUpdate = experiencesUpdate; };

	public JobExperienceMultiUpdateMessage(){
	}

	public JobExperienceMultiUpdateMessage(List<JobExperience> experiencesUpdate){
		this.experiencesUpdate = experiencesUpdate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.experiencesUpdate.size());
			int _loc2_ = 0;
			while( _loc2_ < this.experiencesUpdate.size()){
				this.experiencesUpdate.get(_loc2_).Serialize(writer);
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
			this.experiencesUpdate = new ArrayList<JobExperience>();
			while( _loc3_ <  _loc2_){
				JobExperience _loc15_ = new JobExperience();
				_loc15_.Deserialize(reader);
				this.experiencesUpdate.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
