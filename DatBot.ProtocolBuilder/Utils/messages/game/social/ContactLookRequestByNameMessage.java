package protocol.network.messages.game.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.social.ContactLookRequestMessage;

@SuppressWarnings("unused")
public class ContactLookRequestByNameMessage extends ContactLookRequestMessage {
	public static final int ProtocolId = 5933;

	private String playerName;

	public String getPlayerName() { return this.playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; };

	public ContactLookRequestByNameMessage(){
	}

	public ContactLookRequestByNameMessage(String playerName){
		this.playerName = playerName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.playerName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
