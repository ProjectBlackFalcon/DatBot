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
import protocol.network.types.game.context.roleplay.party.PartyInvitationMemberInformations;
import protocol.network.types.game.context.roleplay.party.PartyGuestInformations;

@SuppressWarnings("unused")
public class PartyInvitationDetailsMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 6263;

	private int partyType;
	private String partyName;
	private long fromId;
	private String fromName;
	private long leaderId;
	private List<PartyInvitationMemberInformations> members;
	private List<PartyGuestInformations> guests;

	public int getPartyType() { return this.partyType; };
	public void setPartyType(int partyType) { this.partyType = partyType; };
	public String getPartyName() { return this.partyName; };
	public void setPartyName(String partyName) { this.partyName = partyName; };
	public long getFromId() { return this.fromId; };
	public void setFromId(long fromId) { this.fromId = fromId; };
	public String getFromName() { return this.fromName; };
	public void setFromName(String fromName) { this.fromName = fromName; };
	public long getLeaderId() { return this.leaderId; };
	public void setLeaderId(long leaderId) { this.leaderId = leaderId; };
	public List<PartyInvitationMemberInformations> getMembers() { return this.members; };
	public void setMembers(List<PartyInvitationMemberInformations> members) { this.members = members; };
	public List<PartyGuestInformations> getGuests() { return this.guests; };
	public void setGuests(List<PartyGuestInformations> guests) { this.guests = guests; };

	public PartyInvitationDetailsMessage(){
	}

	public PartyInvitationDetailsMessage(int partyType, String partyName, long fromId, String fromName, long leaderId, List<PartyInvitationMemberInformations> members, List<PartyGuestInformations> guests){
		this.partyType = partyType;
		this.partyName = partyName;
		this.fromId = fromId;
		this.fromName = fromName;
		this.leaderId = leaderId;
		this.members = members;
		this.guests = guests;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.partyType);
			writer.writeUTF(this.partyName);
			writer.writeVarLong(this.fromId);
			writer.writeUTF(this.fromName);
			writer.writeVarLong(this.leaderId);
			writer.writeShort(this.members.size());
			int _loc2_ = 0;
			while( _loc2_ < this.members.size()){
				this.members.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.guests.size());
			int _loc3_ = 0;
			while( _loc3_ < this.guests.size()){
				this.guests.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
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
			this.fromId = reader.readVarLong();
			this.fromName = reader.readUTF();
			this.leaderId = reader.readVarLong();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.members = new ArrayList<PartyInvitationMemberInformations>();
			while( _loc3_ <  _loc2_){
				PartyInvitationMemberInformations _loc15_ = new PartyInvitationMemberInformations();
				_loc15_.Deserialize(reader);
				this.members.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.guests = new ArrayList<PartyGuestInformations>();
			while( _loc5_ <  _loc4_){
				PartyGuestInformations _loc16_ = new PartyGuestInformations();
				_loc16_.Deserialize(reader);
				this.guests.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
