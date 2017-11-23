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
import protocol.network.types.game.context.roleplay.party.PartyMemberGeoPosition;

@SuppressWarnings("unused")
public class PartyLocateMembersMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 5595;

	public List<PartyMemberGeoPosition> geopositions;

	public PartyLocateMembersMessage(){
	}

	public PartyLocateMembersMessage(List<PartyMemberGeoPosition> geopositions){
		this.geopositions = geopositions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.geopositions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.geopositions.size()){
				this.geopositions.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.geopositions = new ArrayList<PartyMemberGeoPosition>();
			while( _loc3_ <  _loc2_){
				PartyMemberGeoPosition _loc15_ = new PartyMemberGeoPosition();
				_loc15_.Deserialize(reader);
				this.geopositions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(PartyMemberGeoPosition a : geopositions) {
			//Network.appendDebug("geopositions : " + a);
		//}
	//}
}
