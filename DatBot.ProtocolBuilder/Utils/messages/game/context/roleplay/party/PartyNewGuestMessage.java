package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyEventMessage;
import protocol.network.types.game.context.roleplay.party.PartyGuestInformations;

@SuppressWarnings("unused")
public class PartyNewGuestMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 6260;

	private PartyGuestInformations guest;

	public PartyGuestInformations getGuest() { return this.guest; }
	public void setGuest(PartyGuestInformations guest) { this.guest = guest; };

	public PartyNewGuestMessage(){
	}

	public PartyNewGuestMessage(PartyGuestInformations guest){
		this.guest = guest;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			guest.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.guest = new PartyGuestInformations();
			this.guest.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
