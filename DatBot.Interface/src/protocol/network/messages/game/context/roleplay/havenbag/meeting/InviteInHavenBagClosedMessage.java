package protocol.network.messages.game.context.roleplay.havenbag.meeting;

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
import protocol.network.types.game.character.CharacterMinimalInformations;

@SuppressWarnings("unused")
public class InviteInHavenBagClosedMessage extends NetworkMessage {
	public static final int ProtocolId = 6645;

	public CharacterMinimalInformations hostInformations;

	public InviteInHavenBagClosedMessage(){
	}

	public InviteInHavenBagClosedMessage(CharacterMinimalInformations hostInformations){
		this.hostInformations = hostInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			hostInformations.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.hostInformations = new CharacterMinimalInformations();
			this.hostInformations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("hostInformations : " + this.hostInformations);
	//}
}
