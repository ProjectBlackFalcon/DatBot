package protocol.network.types.game.context.roleplay.quest;

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
public class QuestActiveInformations extends NetworkMessage {
	public static final int ProtocolId = 381;

	public int questId;

	public QuestActiveInformations(){
	}

	public QuestActiveInformations(int questId){
		this.questId = questId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.questId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("questId : " + this.questId);
	//}
}
