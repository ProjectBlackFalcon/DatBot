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
import protocol.network.types.game.social.AbstractSocialGroupInfos;

@SuppressWarnings("unused")
public class BasicGuildInformations extends AbstractSocialGroupInfos {
	public static final int ProtocolId = 365;

	public int guildId;
	public String guildName;
	public int guildLevel;

	public BasicGuildInformations(){
	}

	public BasicGuildInformations(int guildId, String guildName, int guildLevel){
		this.guildId = guildId;
		this.guildName = guildName;
		this.guildLevel = guildLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.guildId);
			writer.writeUTF(this.guildName);
			writer.writeByte(this.guildLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.guildId = reader.readVarInt();
			this.guildName = reader.readUTF();
			this.guildLevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("guildId : " + this.guildId);
		//Network.appendDebug("guildName : " + this.guildName);
		//Network.appendDebug("guildLevel : " + this.guildLevel);
	//}
}
