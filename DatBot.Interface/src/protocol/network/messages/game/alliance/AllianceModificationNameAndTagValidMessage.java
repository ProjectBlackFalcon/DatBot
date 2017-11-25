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

	public String allianceName;
	public String allianceTag;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("allianceName : " + this.allianceName);
		//Network.appendDebug("allianceTag : " + this.allianceTag);
	//}
}