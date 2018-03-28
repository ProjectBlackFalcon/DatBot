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
public class AllianceModificationNameAndTagValidMessage extends NetworkMessage {
	public static final int ProtocolId = 6449;

	private String allianceName;
	private String allianceTag;

	public String getAllianceName() { return this.allianceName; }
	public void setAllianceName(String allianceName) { this.allianceName = allianceName; };
	public String getAllianceTag() { return this.allianceTag; }
	public void setAllianceTag(String allianceTag) { this.allianceTag = allianceTag; };

	public AllianceModificationNameAndTagValidMessage(){
	}

	public AllianceModificationNameAndTagValidMessage(String allianceName, String allianceTag){
		this.allianceName = allianceName;
		this.allianceTag = allianceTag;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.allianceName);
			writer.writeUTF(this.allianceTag);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allianceName = reader.readUTF();
			this.allianceTag = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
