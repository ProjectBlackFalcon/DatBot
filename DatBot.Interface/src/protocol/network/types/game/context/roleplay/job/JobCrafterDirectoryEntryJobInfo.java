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
public class JobCrafterDirectoryEntryJobInfo extends NetworkMessage {
	public static final int ProtocolId = 195;

	public int jobId;
	public int jobLevel;
	public boolean free;
	public int minLevel;

	public JobCrafterDirectoryEntryJobInfo(){
	}

	public JobCrafterDirectoryEntryJobInfo(int jobId, int jobLevel, boolean free, int minLevel){
		this.jobId = jobId;
		this.jobLevel = jobLevel;
		this.free = free;
		this.minLevel = minLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.jobId);
			writer.writeByte(this.jobLevel);
			writer.writeBoolean(this.free);
			writer.writeByte(this.minLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.jobId = reader.readByte();
			this.jobLevel = reader.readByte();
			this.free = reader.readBoolean();
			this.minLevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("jobId : " + this.jobId);
		//Network.appendDebug("jobLevel : " + this.jobLevel);
		//Network.appendDebug("free : " + this.free);
		//Network.appendDebug("minLevel : " + this.minLevel);
	//}
}
