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

	public int partyType;
	public long partyLeaderId;
	public int maxParticipants;
	public List<PartyMemberInformations> members;
	public List<PartyGuestInformations> guests;
	public boolean restricted;
	public String partyName;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("partyType : " + this.partyType);
		//Network.appendDebug("partyLeaderId : " + this.partyLeaderId);
		//Network.appendDebug("maxParticipants : " + this.maxParticipants);
		//for(PartyMemberInformations a : members) {
			//Network.appendDebug("members : " + a);
		//}
		//for(PartyGuestInformations a : guests) {
			//Network.appendDebug("guests : " + a);
		//}
		//Network.appendDebug("restricted : " + this.restricted);
		//Network.appendDebug("partyName : " + this.partyName);
	//}
}
