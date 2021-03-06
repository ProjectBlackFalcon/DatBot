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
public class AllianceFactsErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 6423;

	private int allianceId;

	public int getAllianceId() { return this.allianceId; }
	public void setAllianceId(int allianceId) { this.allianceId = allianceId; };

	public AllianceFactsErrorMessage(){
	}

	public AllianceFactsErrorMessage(int allianceId){
		this.allianceId = allianceId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.allianceId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allianceId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
