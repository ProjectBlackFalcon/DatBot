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
public class AllianceChangeGuildRightsMessage extends NetworkMessage {
	public static final int ProtocolId = 6426;

	public int guildId;
	public int rights;

	public AllianceChangeGuildRightsMessage(){
	}

	public AllianceChangeGuildRightsMessage(int guildId, int rights){
		this.guildId = guildId;
		this.rights = rights;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.guildId);
			writer.writeByte(this.rights);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildId = reader.readVarInt();
			this.rights = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("guildId : " + this.guildId);
		//Network.appendDebug("rights : " + this.rights);
	//}
}