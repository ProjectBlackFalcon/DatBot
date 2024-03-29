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
public class PartyNameSetRequestMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 6503;

	private String partyName;

	public String getPartyName() { return this.partyName; }
	public void setPartyName(String partyName) { this.partyName = partyName; };

	public PartyNameSetRequestMessage(){
	}

	public PartyNameSetRequestMessage(String partyName){
		this.partyName = partyName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.partyName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.partyName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
