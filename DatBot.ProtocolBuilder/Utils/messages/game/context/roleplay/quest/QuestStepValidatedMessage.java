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
public class QuestStepValidatedMessage extends NetworkMessage {
	public static final int ProtocolId = 6099;

	private int questId;
	private int stepId;

	public int getQuestId() { return this.questId; }
	public void setQuestId(int questId) { this.questId = questId; };
	public int getStepId() { return this.stepId; }
	public void setStepId(int stepId) { this.stepId = stepId; };

	public QuestStepValidatedMessage(){
	}

	public QuestStepValidatedMessage(int questId, int stepId){
		this.questId = questId;
		this.stepId = stepId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.questId);
			writer.writeVarShort(this.stepId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questId = reader.readVarShort();
			this.stepId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
