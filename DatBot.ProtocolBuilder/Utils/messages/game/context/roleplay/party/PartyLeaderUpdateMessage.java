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
public class PartyLeaderUpdateMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 5578;

	private long partyLeaderId;

	public long getPartyLeaderId() { return this.partyLeaderId; };
	public void setPartyLeaderId(long partyLeaderId) { this.partyLeaderId = partyLeaderId; };

	public PartyLeaderUpdateMessage(){
	}

	public PartyLeaderUpdateMessage(long partyLeaderId){
		this.partyLeaderId = partyLeaderId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.partyLeaderId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.partyLeaderId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
