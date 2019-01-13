package protocol.network.messages.game.context.roleplay.breach.meeting;

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
public class BreachInvitationCloseMessage extends NetworkMessage {
	public static final int ProtocolId = 6790;

	private CharacterMinimalInformations host;

	public CharacterMinimalInformations getHost() { return this.host; }
	public void setHost(CharacterMinimalInformations host) { this.host = host; };

	public BreachInvitationCloseMessage(){
	}

	public BreachInvitationCloseMessage(CharacterMinimalInformations host){
		this.host = host;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			host.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.host = new CharacterMinimalInformations();
			this.host.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
