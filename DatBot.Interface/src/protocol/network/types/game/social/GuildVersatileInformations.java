package protocol.network.types.game.social;

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

@SuppressWarnings("unused")
public class GuildVersatileInformations extends NetworkMessage {
	public static final int ProtocolId = 435;

	public int guildId;
	public long leaderId;
	public int guildLevel;
	public int nbMembers;

	public GuildVersatileInformations(){
	}

	public GuildVersatileInformations(int guildId, long leaderId, int guildLevel, int nbMembers){
		this.guildId = guildId;
		this.leaderId = leaderId;
		this.guildLevel = guildLevel;
		this.nbMembers = nbMembers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.guildId);
			writer.writeVarLong(this.leaderId);
			writer.writeByte(this.guildLevel);
			writer.writeByte(this.nbMembers);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildId = reader.readVarInt();
			this.leaderId = reader.readVarLong();
			this.guildLevel = reader.readByte();
			this.nbMembers = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("guildId : " + this.guildId);
		//Network.appendDebug("leaderId : " + this.leaderId);
		//Network.appendDebug("guildLevel : " + this.guildLevel);
		//Network.appendDebug("nbMembers : " + this.nbMembers);
	//}
}
