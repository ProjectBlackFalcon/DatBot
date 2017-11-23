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
import protocol.network.types.game.context.roleplay.AllianceInformations;

@SuppressWarnings("unused")
public class AllianceJoinedMessage extends NetworkMessage {
	public static final int ProtocolId = 6402;

	public AllianceInformations allianceInfo;
	public boolean enabled;
	public int leadingGuildId;

	public AllianceJoinedMessage(){
	}

	public AllianceJoinedMessage(AllianceInformations allianceInfo, boolean enabled, int leadingGuildId){
		this.allianceInfo = allianceInfo;
		this.enabled = enabled;
		this.leadingGuildId = leadingGuildId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			allianceInfo.Serialize(writer);
			writer.writeBoolean(this.enabled);
			writer.writeVarInt(this.leadingGuildId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allianceInfo = new AllianceInformations();
			this.allianceInfo.Deserialize(reader);
			this.enabled = reader.readBoolean();
			this.leadingGuildId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("allianceInfo : " + this.allianceInfo);
		//Network.appendDebug("enabled : " + this.enabled);
		//Network.appendDebug("leadingGuildId : " + this.leadingGuildId);
	//}
}
