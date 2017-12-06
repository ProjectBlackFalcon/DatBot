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
public class JobBookSubscription extends NetworkMessage {
	public static final int ProtocolId = 500;

	public int jobId;
	public boolean subscribed;

	public JobBookSubscription(){
	}

	public JobBookSubscription(int jobId, boolean subscribed){
		this.jobId = jobId;
		this.subscribed = subscribed;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.jobId);
			writer.writeBoolean(this.subscribed);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.jobId = reader.readByte();
			this.subscribed = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("jobId : " + this.jobId);
		//Network.appendDebug("subscribed : " + this.subscribed);
	//}
}
