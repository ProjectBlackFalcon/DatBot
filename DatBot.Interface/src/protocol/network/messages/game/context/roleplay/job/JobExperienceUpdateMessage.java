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
public class JobExperienceUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5654;

	private JobExperience experiencesUpdate;

	public JobExperience getExperiencesUpdate() { return this.experiencesUpdate; }
	public void setExperiencesUpdate(JobExperience experiencesUpdate) { this.experiencesUpdate = experiencesUpdate; };

	public JobExperienceUpdateMessage(){
	}

	public JobExperienceUpdateMessage(JobExperience experiencesUpdate){
		this.experiencesUpdate = experiencesUpdate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			experiencesUpdate.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.experiencesUpdate = new JobExperience();
			this.experiencesUpdate.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
