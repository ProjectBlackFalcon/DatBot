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
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class GuildJoinedMessage extends NetworkMessage {
	public static final int ProtocolId = 5564;

	public GuildInformations guildInfo;
	public int memberRights;

	public GuildJoinedMessage(){
	}

	public GuildJoinedMessage(GuildInformations guildInfo, int memberRights){
		this.guildInfo = guildInfo;
		this.memberRights = memberRights;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			guildInfo.Serialize(writer);
			writer.writeVarInt(this.memberRights);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildInfo = new GuildInformations();
			this.guildInfo.Deserialize(reader);
			this.memberRights = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("guildInfo : " + this.guildInfo);
		//Network.appendDebug("memberRights : " + this.memberRights);
	//}
}