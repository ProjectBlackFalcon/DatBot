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
import protocol.network.types.game.context.fight.FightTeamMemberCharacterInformations;
import protocol.network.types.game.context.roleplay.BasicAllianceInformations;

@SuppressWarnings("unused")
public class FightTeamMemberWithAllianceCharacterInformations extends FightTeamMemberCharacterInformations {
	public static final int ProtocolId = 426;

	public BasicAllianceInformations allianceInfos;

	public FightTeamMemberWithAllianceCharacterInformations(){
	}

	public FightTeamMemberWithAllianceCharacterInformations(BasicAllianceInformations allianceInfos){
		this.allianceInfos = allianceInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			allianceInfos.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allianceInfos = new BasicAllianceInformations();
			this.allianceInfos.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("allianceInfos : " + this.allianceInfos);
	//}
}
