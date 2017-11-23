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
import protocol.network.types.game.character.choice.RemodelingInformation;

@SuppressWarnings("unused")
public class CharacterSelectionWithRemodelMessage extends CharacterSelectionMessage {
	public static final int ProtocolId = 6549;

	public RemodelingInformation remodel;

	public CharacterSelectionWithRemodelMessage(){
	}

	public CharacterSelectionWithRemodelMessage(RemodelingInformation remodel){
		this.remodel = remodel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			remodel.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.remodel = new RemodelingInformation();
			this.remodel.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("remodel : " + this.remodel);
	//}
}
