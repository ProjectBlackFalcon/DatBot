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

	public long memberId;
	public int rank;
	public int experienceGivenPercent;
	public int rights;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("memberId : " + this.memberId);
		//Network.appendDebug("rank : " + this.rank);
		//Network.appendDebug("experienceGivenPercent : " + this.experienceGivenPercent);
		//Network.appendDebug("rights : " + this.rights);
	//}
}