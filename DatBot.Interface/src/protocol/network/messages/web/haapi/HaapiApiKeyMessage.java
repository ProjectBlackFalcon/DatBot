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

	public int returnType;
	public int keyType;
	public String token;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("returnType : " + this.returnType);
		//Network.appendDebug("keyType : " + this.keyType);
		//Network.appendDebug("token : " + this.token);
	//}
}