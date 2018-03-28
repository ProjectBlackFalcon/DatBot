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
public class CheckFileRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6154;

	private String filename;
	private int type;

	public String getFilename() { return this.filename; }
	public void setFilename(String filename) { this.filename = filename; };
	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };

	public CheckFileRequestMessage(){
	}

	public CheckFileRequestMessage(String filename, int type){
		this.filename = filename;
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.filename);
			writer.writeByte(this.type);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.filename = reader.readUTF();
			this.type = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
