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

	private int guildId;
	private String guildName;
	private int guildLevel;

	public int getGuildId() { return this.guildId; }
	public void setGuildId(int guildId) { this.guildId = guildId; };
	public String getGuildName() { return this.guildName; }
	public void setGuildName(String guildName) { this.guildName = guildName; };
	public int getGuildLevel() { return this.guildLevel; }
	public void setGuildLevel(int guildLevel) { this.guildLevel = guildLevel; };

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
	}

}
