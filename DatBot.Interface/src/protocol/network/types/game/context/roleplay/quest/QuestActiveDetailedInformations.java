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
import protocol.network.types.game.context.roleplay.quest.QuestActiveInformations;
import protocol.network.types.game.context.roleplay.quest.QuestObjectiveInformations;

@SuppressWarnings("unused")
public class QuestActiveDetailedInformations extends QuestActiveInformations {
	public static final int ProtocolId = 382;

	public int stepId;
	public List<QuestObjectiveInformations> objectives;

	public QuestActiveDetailedInformations(){
	}

	public QuestActiveDetailedInformations(int stepId, List<QuestObjectiveInformations> objectives){
		this.stepId = stepId;
		this.objectives = objectives;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.stepId);
			writer.writeShort(this.objectives.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectives.size()){
				writer.writeShort(QuestObjectiveInformations.ProtocolId);
				this.objectives.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.stepId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objectives = new ArrayList<QuestObjectiveInformations>();
			while( _loc3_ <  _loc2_){
				QuestObjectiveInformations _loc15_ = (QuestObjectiveInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.objectives.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("stepId : " + this.stepId);
		//for(QuestObjectiveInformations a : objectives) {
			//Network.appendDebug("objectives : " + a);
		//}
	//}
}
