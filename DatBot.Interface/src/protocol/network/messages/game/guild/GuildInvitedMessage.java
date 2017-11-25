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

	public long recruterId;
	public String recruterName;
	public BasicGuildInformations guildInfo;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("recruterId : " + this.recruterId);
		//Network.appendDebug("recruterName : " + this.recruterName);
		//Network.appendDebug("guildInfo : " + this.guildInfo);
	//}
}