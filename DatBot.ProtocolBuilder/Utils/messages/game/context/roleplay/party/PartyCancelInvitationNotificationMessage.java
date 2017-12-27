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
public class PartyCancelInvitationNotificationMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 6251;

	private long cancelerId;
	private long guestId;

	public long getCancelerId() { return this.cancelerId; };
	public void setCancelerId(long cancelerId) { this.cancelerId = cancelerId; };
	public long getGuestId() { return this.guestId; };
	public void setGuestId(long guestId) { this.guestId = guestId; };

	public PartyCancelInvitationNotificationMessage(){
	}

	public PartyCancelInvitationNotificationMessage(long cancelerId, long guestId){
		this.cancelerId = cancelerId;
		this.guestId = guestId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.cancelerId);
			writer.writeVarLong(this.guestId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.cancelerId = reader.readVarLong();
			this.guestId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
