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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class AbstractPartyMessage extends NetworkMessage {
	public static final int ProtocolId = 6274;

	private int partyId;

	public int getPartyId() { return this.partyId; }
	public void setPartyId(int partyId) { this.partyId = partyId; };

	public AbstractPartyMessage(){
	}

	public AbstractPartyMessage(int partyId){
		this.partyId = partyId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.partyId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.partyId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
