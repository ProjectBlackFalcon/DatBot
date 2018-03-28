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
public class GuildMemberOnlineStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6061;

	private long memberId;
	private boolean online;

	public long getMemberId() { return this.memberId; }
	public void setMemberId(long memberId) { this.memberId = memberId; };
	public boolean isOnline() { return this.online; }
	public void setOnline(boolean online) { this.online = online; };

	public GuildMemberOnlineStatusMessage(){
	}

	public GuildMemberOnlineStatusMessage(long memberId, boolean online){
		this.memberId = memberId;
		this.online = online;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.memberId);
			writer.writeBoolean(this.online);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.memberId = reader.readVarLong();
			this.online = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
