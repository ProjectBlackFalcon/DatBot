package protocol.network.messages.game.context.roleplay.quest;

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
public class QuestStartRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5643;

	private int questId;

	public int getQuestId() { return this.questId; };
	public void setQuestId(int questId) { this.questId = questId; };

	public QuestStartRequestMessage(){
	}

	public QuestStartRequestMessage(int questId){
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

}
