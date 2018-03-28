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

	private GuildInformations guildInfo;
	private int memberRights;

	public GuildInformations getGuildInfo() { return this.guildInfo; }
	public void setGuildInfo(GuildInformations guildInfo) { this.guildInfo = guildInfo; };
	public int getMemberRights() { return this.memberRights; }
	public void setMemberRights(int memberRights) { this.memberRights = memberRights; };

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
	}

}
