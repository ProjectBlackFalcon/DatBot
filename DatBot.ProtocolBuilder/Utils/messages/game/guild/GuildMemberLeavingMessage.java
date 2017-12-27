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
public class GuildMemberLeavingMessage extends NetworkMessage {
	public static final int ProtocolId = 5923;

	private boolean kicked;
	private long memberId;

	public boolean isKicked() { return this.kicked; };
	public void setKicked(boolean kicked) { this.kicked = kicked; };
	public long getMemberId() { return this.memberId; };
	public void setMemberId(long memberId) { this.memberId = memberId; };

	public GuildMemberLeavingMessage(){
	}

	public GuildMemberLeavingMessage(boolean kicked, long memberId){
		this.kicked = kicked;
		this.memberId = memberId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.kicked);
			writer.writeVarLong(this.memberId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.kicked = reader.readBoolean();
			this.memberId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
