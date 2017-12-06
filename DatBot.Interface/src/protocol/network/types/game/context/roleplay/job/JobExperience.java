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

	public int jobId;
	public int jobLevel;
	public long jobXP;
	public long jobXpLevelFloor;
	public long jobXpNextLevelFloor;

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

	//private void append(){
		//Network.appendDebug("jobId : " + this.jobId);
		//Network.appendDebug("jobLevel : " + this.jobLevel);
		//Network.appendDebug("jobXP : " + this.jobXP);
		//Network.appendDebug("jobXpLevelFloor : " + this.jobXpLevelFloor);
		//Network.appendDebug("jobXpNextLevelFloor : " + this.jobXpNextLevelFloor);
	//}
}
