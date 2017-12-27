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
import protocol.network.types.game.context.roleplay.party.PartyMemberInformations;
import protocol.network.types.game.context.roleplay.party.PartyGuestInformations;

@SuppressWarnings("unused")
public class PartyJoinMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 5576;

	private int partyType;
	private long partyLeaderId;
	private int maxParticipants;
	private List<PartyMemberInformations> members;
	private List<PartyGuestInformations> guests;
	private boolean restricted;
	private String partyName;

	public int getPartyType() { return this.partyType; };
	public void setPartyType(int partyType) { this.partyType = partyType; };
	public long getPartyLeaderId() { return this.partyLeaderId; };
	public void setPartyLeaderId(long partyLeaderId) { this.partyLeaderId = partyLeaderId; };
	public int getMaxParticipants() { return this.maxParticipants; };
	public void setMaxParticipants(int maxParticipants) { this.maxParticipants = maxParticipants; };
	public List<PartyMemberInformations> getMembers() { return this.members; };
	public void setMembers(List<PartyMemberInformations> members) { this.members = members; };
	public List<PartyGuestInformations> getGuests() { return this.guests; };
	public void setGuests(List<PartyGuestInformations> guests) { this.guests = guests; };
	public boolean isRestricted() { return this.restricted; };
	public void setRestricted(boolean restricted) { this.restricted = restricted; };
	public String getPartyName() { return this.partyName; };
	public void setPartyName(String partyName) { this.partyName = partyName; };

	public PartyJoinMessage(){
	}

	public PartyJoinMessage(int partyType, long partyLeaderId, int maxParticipants, List<PartyMemberInformations> members, List<PartyGuestInformations> guests, boolean restricted, String partyName){
		this.partyType = partyType;
		this.partyLeaderId = partyLeaderId;
		this.maxParticipants = maxParticipants;
		this.members = members;
		this.guests = guests;
		this.restricted = restricted;
		this.partyName = partyName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.partyType);
			writer.writeVarLong(this.partyLeaderId);
			writer.writeByte(this.maxParticipants);
			writer.writeShort(this.members.size());
			int _loc2_ = 0;
			while( _loc2_ < this.members.size()){
				writer.writeShort(PartyMemberInformations.ProtocolId);
				this.members.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.guests.size());
			int _loc3_ = 0;
			while( _loc3_ < this.guests.size()){
				this.guests.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
			writer.writeBoolean(this.restricted);
			writer.writeUTF(this.partyName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.partyType = reader.readByte();
			this.partyLeaderId = reader.readVarLong();
			this.maxParticipants = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.members = new ArrayList<PartyMemberInformations>();
			while( _loc3_ <  _loc2_){
				PartyMemberInformations _loc15_ = (PartyMemberInformations) ProtocolTypeManager.getInstance(reader.readShort());
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
			this.restricted = reader.readBoolean();
			this.partyName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
