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

	private int guildId;
	private long leaderId;
	private int guildLevel;
	private int nbMembers;

	public int getGuildId() { return this.guildId; };
	public void setGuildId(int guildId) { this.guildId = guildId; };
	public long getLeaderId() { return this.leaderId; };
	public void setLeaderId(long leaderId) { this.leaderId = leaderId; };
	public int getGuildLevel() { return this.guildLevel; };
	public void setGuildLevel(int guildLevel) { this.guildLevel = guildLevel; };
	public int getNbMembers() { return this.nbMembers; };
	public void setNbMembers(int nbMembers) { this.nbMembers = nbMembers; };

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
	}

}
