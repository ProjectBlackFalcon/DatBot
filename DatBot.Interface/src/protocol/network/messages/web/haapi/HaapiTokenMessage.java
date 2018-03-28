package protocol.network.messages.web.haapi;

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
public class HaapiTokenMessage extends NetworkMessage {
	public static final int ProtocolId = 6767;

	private String token;

	public String getToken() { return this.token; }
	public void setToken(String token) { this.token = token; };

	public HaapiTokenMessage(){
	}

	public HaapiTokenMessage(String token){
		this.token = token;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.token);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.token = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
