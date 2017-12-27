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
public class GuildInvitationMessage extends NetworkMessage {
	public static final int ProtocolId = 5551;

	private long targetId;

	public long getTargetId() { return this.targetId; };
	public void setTargetId(long targetId) { this.targetId = targetId; };

	public GuildInvitationMessage(){
	}

	public GuildInvitationMessage(long targetId){
		this.targetId = targetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.targetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.targetId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
