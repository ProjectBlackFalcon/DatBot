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

@SuppressWarnings("unused")
public class GuildChangeMemberParametersMessage extends NetworkMessage {
	public static final int ProtocolId = 5549;

	private long memberId;
	private int rank;
	private int experienceGivenPercent;
	private int rights;

	public long getMemberId() { return this.memberId; }
	public void setMemberId(long memberId) { this.memberId = memberId; };
	public int getRank() { return this.rank; }
	public void setRank(int rank) { this.rank = rank; };
	public int getExperienceGivenPercent() { return this.experienceGivenPercent; }
	public void setExperienceGivenPercent(int experienceGivenPercent) { this.experienceGivenPercent = experienceGivenPercent; };
	public int getRights() { return this.rights; }
	public void setRights(int rights) { this.rights = rights; };

	public GuildChangeMemberParametersMessage(){
	}

	public GuildChangeMemberParametersMessage(long memberId, int rank, int experienceGivenPercent, int rights){
		this.memberId = memberId;
		this.rank = rank;
		this.experienceGivenPercent = experienceGivenPercent;
		this.rights = rights;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.memberId);
			writer.writeVarShort(this.rank);
			writer.writeByte(this.experienceGivenPercent);
			writer.writeVarInt(this.rights);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.memberId = reader.readVarLong();
			this.rank = reader.readVarShort();
			this.experienceGivenPercent = reader.readByte();
			this.rights = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
