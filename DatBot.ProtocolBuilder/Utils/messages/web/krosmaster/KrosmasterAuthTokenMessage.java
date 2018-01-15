package protocol.network.messages.web.krosmaster;

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
public class KrosmasterAuthTokenMessage extends NetworkMessage {
	public static final int ProtocolId = 6351;

	private String token;

	public String getToken() { return this.token; };
	public void setToken(String token) { this.token = token; };

	public KrosmasterAuthTokenMessage(){
	}

	public KrosmasterAuthTokenMessage(String token){
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