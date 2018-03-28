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
import protocol.network.messages.game.context.roleplay.party.AbstractPartyMessage;

@SuppressWarnings("unused")
public class PartyInvitationCancelledForGuestMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 6256;

	private long cancelerId;

	public long getCancelerId() { return this.cancelerId; }
	public void setCancelerId(long cancelerId) { this.cancelerId = cancelerId; };

	public PartyInvitationCancelledForGuestMessage(){
	}

	public PartyInvitationCancelledForGuestMessage(long cancelerId){
		this.cancelerId = cancelerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.cancelerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.cancelerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
