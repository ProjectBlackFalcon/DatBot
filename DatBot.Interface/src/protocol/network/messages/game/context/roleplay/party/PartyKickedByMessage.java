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
public class PartyKickedByMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 5590;

	private long kickerId;

	public long getKickerId() { return this.kickerId; }
	public void setKickerId(long kickerId) { this.kickerId = kickerId; };

	public PartyKickedByMessage(){
	}

	public PartyKickedByMessage(long kickerId){
		this.kickerId = kickerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.kickerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.kickerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
