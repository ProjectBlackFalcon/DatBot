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
import protocol.network.types.game.context.roleplay.quest.QuestObjectiveInformations;

@SuppressWarnings("unused")
public class QuestObjectiveInformationsWithCompletion extends QuestObjectiveInformations {
	public static final int ProtocolId = 386;

	private int curCompletion;
	private int maxCompletion;

	public int getCurCompletion() { return this.curCompletion; }
	public void setCurCompletion(int curCompletion) { this.curCompletion = curCompletion; };
	public int getMaxCompletion() { return this.maxCompletion; }
	public void setMaxCompletion(int maxCompletion) { this.maxCompletion = maxCompletion; };

	public QuestObjectiveInformationsWithCompletion(){
	}

	public QuestObjectiveInformationsWithCompletion(int curCompletion, int maxCompletion){
		this.curCompletion = curCompletion;
		this.maxCompletion = maxCompletion;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.curCompletion);
			writer.writeVarShort(this.maxCompletion);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.curCompletion = reader.readVarShort();
			this.maxCompletion = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
