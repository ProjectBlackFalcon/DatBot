package protocol.network.messages.connection;

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
public class SelectedServerRefusedMessage extends NetworkMessage {
	public static final int ProtocolId = 41;

	public int serverId;
	public int error;
	public int serverStatus;

	public SelectedServerRefusedMessage(){
	}

	public SelectedServerRefusedMessage(int serverId, int error, int serverStatus){
		this.serverId = serverId;
		this.error = error;
		this.serverStatus = serverStatus;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.serverId);
			writer.writeByte(this.error);
			writer.writeByte(this.serverStatus);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.serverId = reader.readVarShort();
			this.error = reader.readByte();
			this.serverStatus = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("serverId : " + this.serverId);
		//Network.appendDebug("error : " + this.error);
		//Network.appendDebug("serverStatus : " + this.serverStatus);
	//}
}
