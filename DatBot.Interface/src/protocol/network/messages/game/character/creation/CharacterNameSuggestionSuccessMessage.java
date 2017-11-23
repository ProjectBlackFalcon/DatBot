package protocol.network.messages.game.character.creation;

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
public class CharacterNameSuggestionSuccessMessage extends NetworkMessage {
	public static final int ProtocolId = 5544;

	public String suggestion;

	public CharacterNameSuggestionSuccessMessage(){
	}

	public CharacterNameSuggestionSuccessMessage(String suggestion){
		this.suggestion = suggestion;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.suggestion);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.suggestion = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("suggestion : " + this.suggestion);
	//}
}
