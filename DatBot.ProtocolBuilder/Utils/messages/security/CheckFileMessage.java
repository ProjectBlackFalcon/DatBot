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

	private String filenameHash;
	private int type;
	private String value;

	public String getFilenameHash() { return this.filenameHash; }
	public void setFilenameHash(String filenameHash) { this.filenameHash = filenameHash; };
	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };
	public String getValue() { return this.value; }
	public void setValue(String value) { this.value = value; };

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
	}

}
