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
public class AllianceKickRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6400;

	private int kickedId;

	public int getKickedId() { return this.kickedId; }
	public void setKickedId(int kickedId) { this.kickedId = kickedId; };

	public AllianceKickRequestMessage(){
	}

	public AllianceKickRequestMessage(int kickedId){
		this.kickedId = kickedId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.kickedId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.kickedId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
