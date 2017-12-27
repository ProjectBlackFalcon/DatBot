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
public class InviteInHavenBagMessage extends NetworkMessage {
	public static final int ProtocolId = 6642;

	private CharacterMinimalInformations guestInformations;
	private boolean accept;

	public CharacterMinimalInformations getGuestInformations() { return this.guestInformations; };
	public void setGuestInformations(CharacterMinimalInformations guestInformations) { this.guestInformations = guestInformations; };
	public boolean isAccept() { return this.accept; };
	public void setAccept(boolean accept) { this.accept = accept; };

	public InviteInHavenBagMessage(){
	}

	public InviteInHavenBagMessage(CharacterMinimalInformations guestInformations, boolean accept){
		this.guestInformations = guestInformations;
		this.accept = accept;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			guestInformations.Serialize(writer);
			writer.writeBoolean(this.accept);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guestInformations = new CharacterMinimalInformations();
			this.guestInformations.Deserialize(reader);
			this.accept = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
