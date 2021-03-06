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
import protocol.network.types.game.context.roleplay.party.PartyMemberInformations;

@SuppressWarnings("unused")
public class PartyUpdateMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 5575;

	private PartyMemberInformations memberInformations;

	public PartyMemberInformations getMemberInformations() { return this.memberInformations; }
	public void setMemberInformations(PartyMemberInformations memberInformations) { this.memberInformations = memberInformations; };

	public PartyUpdateMessage(){
	}

	public PartyUpdateMessage(PartyMemberInformations memberInformations){
		this.memberInformations = memberInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(PartyMemberInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.memberInformations = (PartyMemberInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.memberInformations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
