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
public class AllianceCreationValidMessage extends NetworkMessage {
	public static final int ProtocolId = 6393;

	public String allianceName;
	public String allianceTag;
	public GuildEmblem allianceEmblem;

	public AllianceCreationValidMessage(){
	}

	public AllianceCreationValidMessage(String allianceName, String allianceTag, GuildEmblem allianceEmblem){
		this.allianceName = allianceName;
		this.allianceTag = allianceTag;
		this.allianceEmblem = allianceEmblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.allianceName);
			writer.writeUTF(this.allianceTag);
			allianceEmblem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allianceName = reader.readUTF();
			this.allianceTag = reader.readUTF();
			this.allianceEmblem = new GuildEmblem();
			this.allianceEmblem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("allianceName : " + this.allianceName);
		//Network.appendDebug("allianceTag : " + this.allianceTag);
		//Network.appendDebug("allianceEmblem : " + this.allianceEmblem);
	//}
}