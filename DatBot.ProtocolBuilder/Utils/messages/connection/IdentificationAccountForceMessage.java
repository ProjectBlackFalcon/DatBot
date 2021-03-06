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
import protocol.network.messages.connection.IdentificationMessage;

@SuppressWarnings("unused")
public class IdentificationAccountForceMessage extends IdentificationMessage {
	public static final int ProtocolId = 6119;

	private String forcedAccountLogin;

	public String getForcedAccountLogin() { return this.forcedAccountLogin; }
	public void setForcedAccountLogin(String forcedAccountLogin) { this.forcedAccountLogin = forcedAccountLogin; };

	public IdentificationAccountForceMessage(){
	}

	public IdentificationAccountForceMessage(String forcedAccountLogin){
		this.forcedAccountLogin = forcedAccountLogin;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.forcedAccountLogin);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.forcedAccountLogin = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
