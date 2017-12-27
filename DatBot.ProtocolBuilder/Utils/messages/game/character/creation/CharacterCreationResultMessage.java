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
public class CharacterCreationResultMessage extends NetworkMessage {
	public static final int ProtocolId = 161;

	private int result;

	public int getResult() { return this.result; };
	public void setResult(int result) { this.result = result; };

	public CharacterCreationResultMessage(){
	}

	public CharacterCreationResultMessage(int result){
		this.result = result;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.result);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.result = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
