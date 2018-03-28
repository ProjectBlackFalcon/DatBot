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
import protocol.network.types.game.interactive.skill.SkillActionDescription;

@SuppressWarnings("unused")
public class JobDescription extends NetworkMessage {
	public static final int ProtocolId = 101;

	private int jobId;
	private List<SkillActionDescription> skills;

	public int getJobId() { return this.jobId; }
	public void setJobId(int jobId) { this.jobId = jobId; };
	public List<SkillActionDescription> getSkills() { return this.skills; }
	public void setSkills(List<SkillActionDescription> skills) { this.skills = skills; };

	public JobDescription(){
	}

	public JobDescription(int jobId, List<SkillActionDescription> skills){
		this.jobId = jobId;
		this.skills = skills;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.jobId);
			writer.writeShort(this.skills.size());
			int _loc2_ = 0;
			while( _loc2_ < this.skills.size()){
				writer.writeShort(SkillActionDescription.ProtocolId);
				this.skills.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.jobId = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.skills = new ArrayList<SkillActionDescription>();
			while( _loc3_ <  _loc2_){
				SkillActionDescription _loc15_ = (SkillActionDescription) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.skills.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
