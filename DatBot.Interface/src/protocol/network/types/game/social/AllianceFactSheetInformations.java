package protocol.network.types.game.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.AllianceInformations;

@SuppressWarnings("unused")
public class AllianceFactSheetInformations extends AllianceInformations {
	public static final int ProtocolId = 421;

	public int creationDate;

	public AllianceFactSheetInformations(){
	}

	public AllianceFactSheetInformations(int creationDate){
		this.creationDate = creationDate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.creationDate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.creationDate = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("creationDate : " + this.creationDate);
	//}
}
