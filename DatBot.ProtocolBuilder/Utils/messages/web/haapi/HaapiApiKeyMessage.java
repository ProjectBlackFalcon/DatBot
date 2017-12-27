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
public class HaapiApiKeyMessage extends NetworkMessage {
	public static final int ProtocolId = 6649;

	private int returnType;
	private int keyType;
	private String token;

	public int getReturnType() { return this.returnType; };
	public void setReturnType(int returnType) { this.returnType = returnType; };
	public int getKeyType() { return this.keyType; };
	public void setKeyType(int keyType) { this.keyType = keyType; };
	public String getToken() { return this.token; };
	public void setToken(String token) { this.token = token; };

	public HaapiApiKeyMessage(){
	}

	public HaapiApiKeyMessage(int returnType, int keyType, String token){
		this.returnType = returnType;
		this.keyType = keyType;
		this.token = token;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.returnType);
			writer.writeByte(this.keyType);
			writer.writeUTF(this.token);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.returnType = reader.readByte();
			this.keyType = reader.readByte();
			this.token = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
