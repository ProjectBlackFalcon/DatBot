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
import protocol.network.NetworkMessage;
import protocol.network.types.game.guild.GuildEmblem;

@SuppressWarnings("unused")
public class GuildCreationValidMessage extends NetworkMessage {
	public static final int ProtocolId = 5546;

	private String guildName;
	private GuildEmblem guildEmblem;

	public String getGuildName() { return this.guildName; };
	public void setGuildName(String guildName) { this.guildName = guildName; };
	public GuildEmblem getGuildEmblem() { return this.guildEmblem; };
	public void setGuildEmblem(GuildEmblem guildEmblem) { this.guildEmblem = guildEmblem; };

	public GuildCreationValidMessage(){
	}

	public GuildCreationValidMessage(String guildName, GuildEmblem guildEmblem){
		this.guildName = guildName;
		this.guildEmblem = guildEmblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.guildName);
			guildEmblem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildName = reader.readUTF();
			this.guildEmblem = new GuildEmblem();
			this.guildEmblem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
