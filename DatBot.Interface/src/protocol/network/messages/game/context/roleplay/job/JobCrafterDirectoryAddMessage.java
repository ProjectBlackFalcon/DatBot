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
import protocol.network.types.game.context.roleplay.job.JobCrafterDirectoryListEntry;

@SuppressWarnings("unused")
public class JobCrafterDirectoryAddMessage extends NetworkMessage {
	public static final int ProtocolId = 5651;

	public JobCrafterDirectoryListEntry listEntry;

	public JobCrafterDirectoryAddMessage(){
	}

	public JobCrafterDirectoryAddMessage(JobCrafterDirectoryListEntry listEntry){
		this.listEntry = listEntry;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			listEntry.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.listEntry = new JobCrafterDirectoryListEntry();
			this.listEntry.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("listEntry : " + this.listEntry);
	//}
}
