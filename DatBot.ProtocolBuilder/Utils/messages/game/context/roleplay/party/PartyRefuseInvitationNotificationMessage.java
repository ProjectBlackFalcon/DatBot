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

@SuppressWarnings("unused")
public class PartyRefuseInvitationNotificationMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 5596;

	private long guestId;

	public long getGuestId() { return this.guestId; };
	public void setGuestId(long guestId) { this.guestId = guestId; };

	public PartyRefuseInvitationNotificationMessage(){
	}

	public PartyRefuseInvitationNotificationMessage(long guestId){
		this.guestId = guestId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.guestId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.guestId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}