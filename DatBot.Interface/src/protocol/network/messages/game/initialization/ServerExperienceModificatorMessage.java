package protocol.network.messages.game.initialization;

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
public class ServerExperienceModificatorMessage extends NetworkMessage {
	public static final int ProtocolId = 6237;

	public int experiencePercent;

	public ServerExperienceModificatorMessage(){
	}

	public ServerExperienceModificatorMessage(int experiencePercent){
		this.experiencePercent = experiencePercent;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.experiencePercent);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.experiencePercent = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("experiencePercent : " + this.experiencePercent);
	//}
}