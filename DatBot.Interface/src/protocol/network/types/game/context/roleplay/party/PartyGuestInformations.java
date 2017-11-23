package protocol.network.types.game.context.roleplay.party;

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
import protocol.network.types.game.look.EntityLook;
import protocol.network.types.game.character.status.PlayerStatus;
import protocol.network.types.game.context.roleplay.party.companion.PartyCompanionBaseInformations;

@SuppressWarnings("unused")
public class PartyGuestInformations extends NetworkMessage {
	public static final int ProtocolId = 374;

	public long guestId;
	public long hostId;
	public String name;
	public EntityLook guestLook;
	public int breed;
	public boolean sex;
	public PlayerStatus status;
	public List<PartyCompanionBaseInformations> companions;

	public PartyGuestInformations(){
	}

	public PartyGuestInformations(long guestId, long hostId, String name, EntityLook guestLook, int breed, boolean sex, PlayerStatus status, List<PartyCompanionBaseInformations> companions){
		this.guestId = guestId;
		this.hostId = hostId;
		this.name = name;
		this.guestLook = guestLook;
		this.breed = breed;
		this.sex = sex;
		this.status = status;
		this.companions = companions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.guestId);
			writer.writeVarLong(this.hostId);
			writer.writeUTF(this.name);
			guestLook.Serialize(writer);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
			writer.writeShort(PlayerStatus.ProtocolId);
			writer.writeShort(this.companions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.companions.size()){
				this.companions.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guestId = reader.readVarLong();
			this.hostId = reader.readVarLong();
			this.name = reader.readUTF();
			this.guestLook = new EntityLook();
			this.guestLook.Deserialize(reader);
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
			this.status = (PlayerStatus) ProtocolTypeManager.getInstance(reader.readShort());
			this.status.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.companions = new ArrayList<PartyCompanionBaseInformations>();
			while( _loc3_ <  _loc2_){
				PartyCompanionBaseInformations _loc15_ = new PartyCompanionBaseInformations();
				_loc15_.Deserialize(reader);
				this.companions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("guestId : " + this.guestId);
		//Network.appendDebug("hostId : " + this.hostId);
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("guestLook : " + this.guestLook);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("status : " + this.status);
		//for(PartyCompanionBaseInformations a : companions) {
			//Network.appendDebug("companions : " + a);
		//}
	//}
}
