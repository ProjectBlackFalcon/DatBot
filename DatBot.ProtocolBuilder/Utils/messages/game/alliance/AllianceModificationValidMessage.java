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

	private String allianceName;
	private String allianceTag;
	private GuildEmblem Alliancemblem;

	public String getAllianceName() { return this.allianceName; }
	public void setAllianceName(String allianceName) { this.allianceName = allianceName; };
	public String getAllianceTag() { return this.allianceTag; }
	public void setAllianceTag(String allianceTag) { this.allianceTag = allianceTag; };
	public GuildEmblem getAlliancemblem() { return this.Alliancemblem; }
	public void setAlliancemblem(GuildEmblem Alliancemblem) { this.Alliancemblem = Alliancemblem; };

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
	}

}
