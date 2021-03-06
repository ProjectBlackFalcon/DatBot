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
public class UnfollowQuestObjectiveRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6723;

	private int questId;
	private int objectiveId;

	public int getQuestId() { return this.questId; }
	public void setQuestId(int questId) { this.questId = questId; };
	public int getObjectiveId() { return this.objectiveId; }
	public void setObjectiveId(int objectiveId) { this.objectiveId = objectiveId; };

	public UnfollowQuestObjectiveRequestMessage(){
	}

	public UnfollowQuestObjectiveRequestMessage(int questId, int objectiveId){
		this.questId = questId;
		this.objectiveId = objectiveId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.questId);
			writer.writeShort(this.objectiveId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questId = reader.readVarShort();
			this.objectiveId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
