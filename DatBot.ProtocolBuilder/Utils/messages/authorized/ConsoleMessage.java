package protocol.network.messages.authorized;

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
public class ConsoleMessage extends NetworkMessage {
	public static final int ProtocolId = 75;

	private int type;
	private String content;

	public int getType() { return this.type; };
	public void setType(int type) { this.type = type; };
	public String getContent() { return this.content; };
	public void setContent(String content) { this.content = content; };

	public ConsoleMessage(){
	}

	public ConsoleMessage(int type, String content){
		this.type = type;
		this.content = content;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.type);
			writer.writeUTF(this.content);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readByte();
			this.content = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
