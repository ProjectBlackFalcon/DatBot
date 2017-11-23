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
public class JobCrafterDirectoryRemoveMessage extends NetworkMessage {
	public static final int ProtocolId = 5653;

	public int jobId;
	public long playerId;

	public JobCrafterDirectoryRemoveMessage(){
	}

	public JobCrafterDirectoryRemoveMessage(int jobId, long playerId){
		this.jobId = jobId;
		this.playerId = playerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.jobId);
			writer.writeVarLong(this.playerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.jobId = reader.readByte();
			this.playerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("jobId : " + this.jobId);
		//Network.appendDebug("playerId : " + this.playerId);
	//}
}
