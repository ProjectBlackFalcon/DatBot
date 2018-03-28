package protocol.network.messages.game.approach;

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
public class AuthenticationTicketMessage extends NetworkMessage {
	public static final int ProtocolId = 110;

	private String lang;
	private String ticket;

	public String getLang() { return this.lang; }
	public void setLang(String lang) { this.lang = lang; };
	public String getTicket() { return this.ticket; }
	public void setTicket(String ticket) { this.ticket = ticket; };

	public AuthenticationTicketMessage(){
	}

	public AuthenticationTicketMessage(String lang, String ticket){
		this.lang = lang;
		this.ticket = ticket;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.lang);
			writer.writeUTF(this.ticket);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.lang = reader.readUTF();
			this.ticket = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
