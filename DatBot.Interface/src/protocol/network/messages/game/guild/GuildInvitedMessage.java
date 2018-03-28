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
import protocol.network.types.game.context.roleplay.BasicGuildInformations;

@SuppressWarnings("unused")
public class GuildInvitedMessage extends NetworkMessage {
	public static final int ProtocolId = 5552;

	private long recruterId;
	private String recruterName;
	private BasicGuildInformations guildInfo;

	public long getRecruterId() { return this.recruterId; }
	public void setRecruterId(long recruterId) { this.recruterId = recruterId; };
	public String getRecruterName() { return this.recruterName; }
	public void setRecruterName(String recruterName) { this.recruterName = recruterName; };
	public BasicGuildInformations getGuildInfo() { return this.guildInfo; }
	public void setGuildInfo(BasicGuildInformations guildInfo) { this.guildInfo = guildInfo; };

	public GuildInvitedMessage(){
	}

	public GuildInvitedMessage(long recruterId, String recruterName, BasicGuildInformations guildInfo){
		this.recruterId = recruterId;
		this.recruterName = recruterName;
		this.guildInfo = guildInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.recruterId);
			writer.writeUTF(this.recruterName);
			guildInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.recruterId = reader.readVarLong();
			this.recruterName = reader.readUTF();
			this.guildInfo = new BasicGuildInformations();
			this.guildInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
