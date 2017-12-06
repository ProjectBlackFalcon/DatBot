package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.BasicGuildInformations;
import protocol.network.types.game.guild.GuildEmblem;

@SuppressWarnings("unused")
public class GuildInformations extends BasicGuildInformations {
	public static final int ProtocolId = 127;

	public GuildEmblem guildEmblem;

	public GuildInformations(){
	}

	public GuildInformations(GuildEmblem guildEmblem){
		this.guildEmblem = guildEmblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			guildEmblem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.guildEmblem = new GuildEmblem();
			this.guildEmblem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("guildEmblem : " + this.guildEmblem);
	//}
}
