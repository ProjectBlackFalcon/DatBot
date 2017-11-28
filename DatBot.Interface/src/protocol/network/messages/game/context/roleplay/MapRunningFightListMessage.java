package protocol.network.messages.game.context.roleplay;

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
import protocol.network.types.game.context.fight.FightExternalInformations;
import protocol.network.types.game.context.fight.FightOptionsInformations;
import protocol.network.types.game.context.fight.FightTeamLightInformations;

@SuppressWarnings("unused")
public class MapRunningFightListMessage extends NetworkMessage {
	public static final int ProtocolId = 5743;

	public List<FightExternalInformations> fights;

	public MapRunningFightListMessage(){
	}

	public MapRunningFightListMessage(List<FightExternalInformations> fights){
		this.fights = fights;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.fights.size());
			int _loc2_ = 0;
			while( _loc2_ < this.fights.size()){
				this.fights.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.fights = new ArrayList<FightExternalInformations>();
			while( _loc3_ <  _loc2_){
				FightExternalInformations _loc15_ = new FightExternalInformations();
				_loc15_.Deserialize(reader);
				this.fights.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		append();
	}

	private void append(){
		for(FightExternalInformations a : fights) {
			Network.append("fights : " + a.fightId);
			Network.append("fightStart : " + a.fightStart);
			Network.append("fightType : " + a.fightType);
			Network.append("fightSpectatorLocked : " + a.fightSpectatorLocked);
			for (FightTeamLightInformations b : a.fightTeams) {
				Network.append("\tleaderId : " + b.leaderId);
				Network.append("\tmeanLevel : " + b.meanLevel);
				Network.append("\tnbWaves : " + b.nbWaves);
				Network.append("\tteamId : " + b.teamId);
				Network.append("\tteamMembersCount : " + b.teamMembersCount);
				Network.append("\tteamSide : " + b.teamSide);
				Network.append("\tteamTypeId : " + b.teamTypeId);
				Network.append("\thasAllianceMember : " + b.hasAllianceMember);
				Network.append("\thasFriend : " + b.hasFriend);
				Network.append("\thasGroupMember : " + b.hasGroupMember);
				Network.append("\thasGuildMember : " + b.hasGuildMember);
				Network.append("\thasMyTaxCollector : " + b.hasMyTaxCollector);
			}
			for (FightOptionsInformations c : a.fightTeamsOptions) {
				Network.append("\tisAskingForHelp : " + c.isAskingForHelp);
				Network.append("\tisClosed : " + c.isClosed);
				Network.append("\tisRestrictedToPartyOnly : " + c.isRestrictedToPartyOnly);
				Network.append("\tisSecret : " + c.isSecret);
			}
		}
	}
}
