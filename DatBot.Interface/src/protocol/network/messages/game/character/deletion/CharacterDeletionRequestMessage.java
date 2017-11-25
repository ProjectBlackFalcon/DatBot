package protocol.network.messages.game.character.deletion;

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
public class CharacterDeletionRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 165;

	public long characterId;
	public String secretAnswerHash;

	public CharacterDeletionRequestMessage(){
	}

	public CharacterDeletionRequestMessage(long characterId, String secretAnswerHash){
		this.characterId = characterId;
		this.secretAnswerHash = secretAnswerHash;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.characterId);
			writer.writeUTF(this.secretAnswerHash);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.characterId = reader.readVarLong();
			this.secretAnswerHash = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("characterId : " + this.characterId);
		//Network.appendDebug("secretAnswerHash : " + this.secretAnswerHash);
	//}
}