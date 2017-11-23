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
import protocol.network.types.connection.GameServerInformations;

@SuppressWarnings("unused")
public class ServerStatusUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 50;

	public GameServerInformations server;

	public ServerStatusUpdateMessage(){
	}

	public ServerStatusUpdateMessage(GameServerInformations server){
		this.server = server;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			server.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.server = new GameServerInformations();
			this.server.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("server : " + this.server);
	//}
}
