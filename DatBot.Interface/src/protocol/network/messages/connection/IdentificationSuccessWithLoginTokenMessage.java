package protocol.network.messages.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.connection.IdentificationSuccessMessage;

@SuppressWarnings("unused")
public class IdentificationSuccessWithLoginTokenMessage extends IdentificationSuccessMessage {
	public static final int ProtocolId = 6209;

	public String loginToken;

	public IdentificationSuccessWithLoginTokenMessage(){
	}

	public IdentificationSuccessWithLoginTokenMessage(String loginToken){
		this.loginToken = loginToken;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.loginToken);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.loginToken = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("loginToken : " + this.loginToken);
	//}
}
