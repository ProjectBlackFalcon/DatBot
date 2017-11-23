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
public class JobCrafterDirectorySettings extends NetworkMessage {
	public static final int ProtocolId = 97;

	public int jobId;
	public int minLevel;
	public boolean free;

	public JobCrafterDirectorySettings(){
	}

	public JobCrafterDirectorySettings(int jobId, int minLevel, boolean free){
		this.jobId = jobId;
		this.minLevel = minLevel;
		this.free = free;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.jobId);
			writer.writeByte(this.minLevel);
			writer.writeBoolean(this.free);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.jobId = reader.readByte();
			this.minLevel = reader.readByte();
			this.free = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("jobId : " + this.jobId);
		//Network.appendDebug("minLevel : " + this.minLevel);
		//Network.appendDebug("free : " + this.free);
	//}
}
