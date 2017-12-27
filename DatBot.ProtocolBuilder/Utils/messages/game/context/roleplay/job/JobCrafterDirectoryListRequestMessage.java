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

@SuppressWarnings("unused")
public class JobCrafterDirectoryListRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6047;

	private int jobId;

	public int getJobId() { return this.jobId; };
	public void setJobId(int jobId) { this.jobId = jobId; };

	public JobCrafterDirectoryListRequestMessage(){
	}

	public JobCrafterDirectoryListRequestMessage(int jobId){
		this.jobId = jobId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.jobId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.jobId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
