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

	private String allianceName;
	private String allianceTag;
	private GuildEmblem allianceEmblem;

	public String getAllianceName() { return this.allianceName; }
	public void setAllianceName(String allianceName) { this.allianceName = allianceName; };
	public String getAllianceTag() { return this.allianceTag; }
	public void setAllianceTag(String allianceTag) { this.allianceTag = allianceTag; };
	public GuildEmblem getAllianceEmblem() { return this.allianceEmblem; }
	public void setAllianceEmblem(GuildEmblem allianceEmblem) { this.allianceEmblem = allianceEmblem; };

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
	}

}
