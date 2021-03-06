package protocol.network.types.game.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.GuildInformations;
import protocol.network.types.game.context.roleplay.BasicNamedAllianceInformations;

@SuppressWarnings("unused")
public class AlliancedGuildFactSheetInformations extends GuildInformations {
	public static final int ProtocolId = 422;

	private BasicNamedAllianceInformations allianceInfos;

	public BasicNamedAllianceInformations getAllianceInfos() { return this.allianceInfos; }
	public void setAllianceInfos(BasicNamedAllianceInformations allianceInfos) { this.allianceInfos = allianceInfos; };

	public AlliancedGuildFactSheetInformations(){
	}

	public AlliancedGuildFactSheetInformations(BasicNamedAllianceInformations allianceInfos){
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
			this.allianceInfos = new BasicNamedAllianceInformations();
			this.allianceInfos.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
