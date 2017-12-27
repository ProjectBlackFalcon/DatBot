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
public class PartyPledgeLoyaltyRequestMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 6269;

	private boolean loyal;

	public boolean isLoyal() { return this.loyal; };
	public void setLoyal(boolean loyal) { this.loyal = loyal; };

	public PartyPledgeLoyaltyRequestMessage(){
	}

	public PartyPledgeLoyaltyRequestMessage(boolean loyal){
		this.loyal = loyal;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.loyal);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.loyal = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
