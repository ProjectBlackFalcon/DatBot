package protocol.network.messages.game.alliance;

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
public class AllianceGuildLeavingMessage extends NetworkMessage {
	public static final int ProtocolId = 6399;

	private boolean kicked;
	private int guildId;

	public boolean isKicked() { return this.kicked; };
	public void setKicked(boolean kicked) { this.kicked = kicked; };
	public int getGuildId() { return this.guildId; };
	public void setGuildId(int guildId) { this.guildId = guildId; };

	public AllianceGuildLeavingMessage(){
	}

	public AllianceGuildLeavingMessage(boolean kicked, int guildId){
		this.kicked = kicked;
		this.guildId = guildId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.kicked);
			writer.writeVarInt(this.guildId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.kicked = reader.readBoolean();
			this.guildId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
