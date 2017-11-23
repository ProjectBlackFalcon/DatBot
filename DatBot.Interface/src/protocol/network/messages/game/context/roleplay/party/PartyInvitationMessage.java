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
public class PartyInvitationMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 5586;

	public int partyType;
	public String partyName;
	public int maxParticipants;
	public long fromId;
	public String fromName;
	public long toId;

	public PartyInvitationMessage(){
	}

	public PartyInvitationMessage(int partyType, String partyName, int maxParticipants, long fromId, String fromName, long toId){
		this.partyType = partyType;
		this.partyName = partyName;
		this.maxParticipants = maxParticipants;
		this.fromId = fromId;
		this.fromName = fromName;
		this.toId = toId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.partyType);
			writer.writeUTF(this.partyName);
			writer.writeByte(this.maxParticipants);
			writer.writeVarLong(this.fromId);
			writer.writeUTF(this.fromName);
			writer.writeVarLong(this.toId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.partyType = reader.readByte();
			this.partyName = reader.readUTF();
			this.maxParticipants = reader.readByte();
			this.fromId = reader.readVarLong();
			this.fromName = reader.readUTF();
			this.toId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("partyType : " + this.partyType);
		//Network.appendDebug("partyName : " + this.partyName);
		//Network.appendDebug("maxParticipants : " + this.maxParticipants);
		//Network.appendDebug("fromId : " + this.fromId);
		//Network.appendDebug("fromName : " + this.fromName);
		//Network.appendDebug("toId : " + this.toId);
	//}
}
