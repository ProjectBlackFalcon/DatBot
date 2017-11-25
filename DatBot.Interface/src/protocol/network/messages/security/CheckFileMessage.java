package protocol.network.messages.security;

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
public class CheckFileMessage extends NetworkMessage {
	public static final int ProtocolId = 6156;

	public String filenameHash;
	public int type;
	public String value;

	public CheckFileMessage(){
	}

	public CheckFileMessage(String filenameHash, int type, String value){
		this.filenameHash = filenameHash;
		this.type = type;
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.filenameHash);
			writer.writeByte(this.type);
			writer.writeUTF(this.value);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.filenameHash = reader.readUTF();
			this.type = reader.readByte();
			this.value = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("filenameHash : " + this.filenameHash);
		//Network.appendDebug("type : " + this.type);
		//Network.appendDebug("value : " + this.value);
	//}
}