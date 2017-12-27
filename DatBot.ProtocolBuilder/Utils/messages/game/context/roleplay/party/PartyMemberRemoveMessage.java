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
public class PartyMemberRemoveMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 5579;

	private long leavingPlayerId;

	public long getLeavingPlayerId() { return this.leavingPlayerId; };
	public void setLeavingPlayerId(long leavingPlayerId) { this.leavingPlayerId = leavingPlayerId; };

	public PartyMemberRemoveMessage(){
	}

	public PartyMemberRemoveMessage(long leavingPlayerId){
		this.leavingPlayerId = leavingPlayerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.leavingPlayerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.leavingPlayerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
