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
public class GuildFactsErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 6424;

	private int guildId;

	public int getGuildId() { return this.guildId; }
	public void setGuildId(int guildId) { this.guildId = guildId; };

	public GuildFactsErrorMessage(){
	}

	public GuildFactsErrorMessage(int guildId){
		this.guildId = guildId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.guildId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
