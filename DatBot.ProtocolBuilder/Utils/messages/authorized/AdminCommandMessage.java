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
public class AdminCommandMessage extends NetworkMessage {
	public static final int ProtocolId = 76;

	private String content;

	public String getContent() { return this.content; };
	public void setContent(String content) { this.content = content; };

	public AdminCommandMessage(){
	}

	public AdminCommandMessage(String content){
		this.content = content;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.content);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.content = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
