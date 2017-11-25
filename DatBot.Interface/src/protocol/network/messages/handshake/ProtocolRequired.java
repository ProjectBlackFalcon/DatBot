package protocol.network.messages.handshake;

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
public class ProtocolRequired extends NetworkMessage {
	public static final int ProtocolId = 1;

	public int requiredVersion;
	public int currentVersion;

	public ProtocolRequired(){
	}

	public ProtocolRequired(int requiredVersion, int currentVersion){
		this.requiredVersion = requiredVersion;
		this.currentVersion = currentVersion;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.requiredVersion);
			writer.writeInt(this.currentVersion);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.requiredVersion = reader.readInt();
			this.currentVersion = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("requiredVersion : " + this.requiredVersion);
		//Network.appendDebug("currentVersion : " + this.currentVersion);
	//}
}