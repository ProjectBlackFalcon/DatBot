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
public class DownloadErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 1513;

	public int errorId;
	public String message;
	public String helpUrl;

	public DownloadErrorMessage(){
	}

	public DownloadErrorMessage(int errorId, String message, String helpUrl){
		this.errorId = errorId;
		this.message = message;
		this.helpUrl = helpUrl;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.errorId);
			writer.writeUTF(this.message);
			writer.writeUTF(this.helpUrl);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.errorId = reader.readByte();
			this.message = reader.readUTF();
			this.helpUrl = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("errorId : " + this.errorId);
		//Network.appendDebug("message : " + this.message);
		//Network.appendDebug("helpUrl : " + this.helpUrl);
	//}
}
