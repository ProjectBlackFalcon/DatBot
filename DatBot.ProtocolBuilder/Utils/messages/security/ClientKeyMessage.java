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
public class ClientKeyMessage extends NetworkMessage {
	public static final int ProtocolId = 5607;

	private String key;

	public String getKey() { return this.key; }
	public void setKey(String key) { this.key = key; };

	public ClientKeyMessage(){
	}

	public ClientKeyMessage(String key){
		this.key = key;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.key);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.key = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
