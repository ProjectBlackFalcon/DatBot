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
import protocol.network.messages.game.context.roleplay.job.JobAllowMultiCraftRequestMessage;

@SuppressWarnings("unused")
public class JobMultiCraftAvailableSkillsMessage extends JobAllowMultiCraftRequestMessage {
	public static final int ProtocolId = 5747;

	private long playerId;
	private List<Integer> skills;

	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public List<Integer> getSkills() { return this.skills; }
	public void setSkills(List<Integer> skills) { this.skills = skills; };

	public JobMultiCraftAvailableSkillsMessage(){
	}

	public JobMultiCraftAvailableSkillsMessage(long playerId, List<Integer> skills){
		this.playerId = playerId;
		this.skills = skills;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.playerId);
			writer.writeShort(this.skills.size());
			int _loc2_ = 0;
			while( _loc2_ < this.skills.size()){
				writer.writeVarShort(this.skills.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerId = reader.readVarLong();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.skills = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.skills.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
