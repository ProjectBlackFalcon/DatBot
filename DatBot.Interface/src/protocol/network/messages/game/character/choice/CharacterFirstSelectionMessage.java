package protocol.network.messages.game.character.choice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.character.choice.CharacterSelectionMessage;

@SuppressWarnings("unused")
public class CharacterFirstSelectionMessage extends CharacterSelectionMessage {
	public static final int ProtocolId = 6084;

	public boolean doTutorial;

	public CharacterFirstSelectionMessage(){
	}

	public CharacterFirstSelectionMessage(boolean doTutorial){
		this.doTutorial = doTutorial;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.doTutorial);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.doTutorial = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("doTutorial : " + this.doTutorial);
	//}
}
