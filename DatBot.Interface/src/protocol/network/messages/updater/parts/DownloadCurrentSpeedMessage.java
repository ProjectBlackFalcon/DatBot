package protocol.network.messages.updater.parts;

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
public class DownloadCurrentSpeedMessage extends NetworkMessage {
	public static final int ProtocolId = 1511;

	public int downloadSpeed;

	public DownloadCurrentSpeedMessage(){
	}

	public DownloadCurrentSpeedMessage(int downloadSpeed){
		this.downloadSpeed = downloadSpeed;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.downloadSpeed);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.downloadSpeed = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("downloadSpeed : " + this.downloadSpeed);
	//}
}
