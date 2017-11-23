package protocol.network.messages.game.guild;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.guild.GuildFactsMessage;
import protocol.network.types.game.context.roleplay.BasicNamedAllianceInformations;

@SuppressWarnings("unused")
public class GuildInAllianceFactsMessage extends GuildFactsMessage {
	public static final int ProtocolId = 6422;

	public BasicNamedAllianceInformations allianceInfos;

	public GuildInAllianceFactsMessage(){
	}

	public GuildInAllianceFactsMessage(BasicNamedAllianceInformations allianceInfos){
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
		//append();
	}

	//private void append(){
		//Network.appendDebug("allianceInfos : " + this.allianceInfos);
	//}
}
