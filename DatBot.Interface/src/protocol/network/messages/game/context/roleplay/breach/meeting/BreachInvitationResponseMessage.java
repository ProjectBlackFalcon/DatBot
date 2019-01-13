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
public class BreachInvitationResponseMessage extends NetworkMessage {
	public static final int ProtocolId = 6792;

	private CharacterMinimalInformations guest;
	private boolean accept;

	public CharacterMinimalInformations getGuest() { return this.guest; }
	public void setGuest(CharacterMinimalInformations guest) { this.guest = guest; };
	public boolean isAccept() { return this.accept; }
	public void setAccept(boolean accept) { this.accept = accept; };

	public BreachInvitationResponseMessage(){
	}

	public BreachInvitationResponseMessage(CharacterMinimalInformations guest, boolean accept){
		this.guest = guest;
		this.accept = accept;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			guest.Serialize(writer);
			writer.writeBoolean(this.accept);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guest = new CharacterMinimalInformations();
			this.guest.Deserialize(reader);
			this.accept = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
