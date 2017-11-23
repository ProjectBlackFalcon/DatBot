package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.AbstractFightTeamInformations;
import protocol.network.types.game.context.fight.FightTeamMemberInformations;

@SuppressWarnings("unused")
public class FightTeamInformations extends AbstractFightTeamInformations {
	public static final int ProtocolId = 33;

	public List<FightTeamMemberInformations> teamMembers;

	public FightTeamInformations(){
	}

	public FightTeamInformations(List<FightTeamMemberInformations> teamMembers){
		this.teamMembers = teamMembers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.teamMembers.size());
			int _loc2_ = 0;
			while( _loc2_ < this.teamMembers.size()){
				writer.writeShort(FightTeamMemberInformations.ProtocolId);
				this.teamMembers.get(_loc2_).Serialize(writer);
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
			this.teamMembers = new ArrayList<FightTeamMemberInformations>();
			while( _loc3_ <  _loc2_){
				FightTeamMemberInformations _loc15_ = (FightTeamMemberInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.teamMembers.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(FightTeamMemberInformations a : teamMembers) {
			//Network.appendDebug("teamMembers : " + a);
		//}
	//}
}
