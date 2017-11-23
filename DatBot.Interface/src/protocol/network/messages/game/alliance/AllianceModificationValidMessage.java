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
public class AllianceModificationValidMessage extends NetworkMessage {
	public static final int ProtocolId = 6450;

	public String allianceName;
	public String allianceTag;
	public GuildEmblem Alliancemblem;

	public AllianceModificationValidMessage(){
	}

	public AllianceModificationValidMessage(String allianceName, String allianceTag, GuildEmblem Alliancemblem){
		this.allianceName = allianceName;
		this.allianceTag = allianceTag;
		this.Alliancemblem = Alliancemblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.allianceName);
			writer.writeUTF(this.allianceTag);
			Alliancemblem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allianceName = reader.readUTF();
			this.allianceTag = reader.readUTF();
			this.Alliancemblem = new GuildEmblem();
			this.Alliancemblem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("allianceName : " + this.allianceName);
		//Network.appendDebug("allianceTag : " + this.allianceTag);
		//Network.appendDebug("Alliancemblem : " + this.Alliancemblem);
	//}
}
