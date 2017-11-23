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
public class GuildModificationEmblemValidMessage extends NetworkMessage {
	public static final int ProtocolId = 6328;

	public GuildEmblem guildEmblem;

	public GuildModificationEmblemValidMessage(){
	}

	public GuildModificationEmblemValidMessage(GuildEmblem guildEmblem){
		this.guildEmblem = guildEmblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			guildEmblem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildEmblem = new GuildEmblem();
			this.guildEmblem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("guildEmblem : " + this.guildEmblem);
	//}
}
