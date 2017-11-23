package protocol.network.messages.game.alliance;

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
import protocol.network.types.game.guild.GuildEmblem;

@SuppressWarnings("unused")
public class AllianceModificationEmblemValidMessage extends NetworkMessage {
	public static final int ProtocolId = 6447;

	public GuildEmblem Alliancemblem;

	public AllianceModificationEmblemValidMessage(){
	}

	public AllianceModificationEmblemValidMessage(GuildEmblem Alliancemblem){
		this.Alliancemblem = Alliancemblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			Alliancemblem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.Alliancemblem = new GuildEmblem();
			this.Alliancemblem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("Alliancemblem : " + this.Alliancemblem);
	//}
}
