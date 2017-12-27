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
import protocol.network.types.game.context.roleplay.quest.QuestActiveInformations;

@SuppressWarnings("unused")
public class QuestListMessage extends NetworkMessage {
	public static final int ProtocolId = 5626;

	private List<Integer> finishedQuestsIds;
	private List<Integer> finishedQuestsCounts;
	private List<QuestActiveInformations> activeQuests;
	private List<Integer> reinitDoneQuestsIds;

	public List<Integer> getFinishedQuestsIds() { return this.finishedQuestsIds; };
	public void setFinishedQuestsIds(List<Integer> finishedQuestsIds) { this.finishedQuestsIds = finishedQuestsIds; };
	public List<Integer> getFinishedQuestsCounts() { return this.finishedQuestsCounts; };
	public void setFinishedQuestsCounts(List<Integer> finishedQuestsCounts) { this.finishedQuestsCounts = finishedQuestsCounts; };
	public List<QuestActiveInformations> getActiveQuests() { return this.activeQuests; };
	public void setActiveQuests(List<QuestActiveInformations> activeQuests) { this.activeQuests = activeQuests; };
	public List<Integer> getReinitDoneQuestsIds() { return this.reinitDoneQuestsIds; };
	public void setReinitDoneQuestsIds(List<Integer> reinitDoneQuestsIds) { this.reinitDoneQuestsIds = reinitDoneQuestsIds; };

	public QuestListMessage(){
	}

	public QuestListMessage(List<Integer> finishedQuestsIds, List<Integer> finishedQuestsCounts, List<QuestActiveInformations> activeQuests, List<Integer> reinitDoneQuestsIds){
		this.finishedQuestsIds = finishedQuestsIds;
		this.finishedQuestsCounts = finishedQuestsCounts;
		this.activeQuests = activeQuests;
		this.reinitDoneQuestsIds = reinitDoneQuestsIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.finishedQuestsIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.finishedQuestsIds.size()){
				writer.writeVarShort(this.finishedQuestsIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.finishedQuestsCounts.size());
			int _loc3_ = 0;
			while( _loc3_ < this.finishedQuestsCounts.size()){
				writer.writeVarShort(this.finishedQuestsCounts.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.activeQuests.size());
			int _loc4_ = 0;
			while( _loc4_ < this.activeQuests.size()){
				writer.writeShort(QuestActiveInformations.ProtocolId);
				this.activeQuests.get(_loc4_).Serialize(writer);
				_loc4_++;
			}
			writer.writeShort(this.reinitDoneQuestsIds.size());
			int _loc5_ = 0;
			while( _loc5_ < this.reinitDoneQuestsIds.size()){
				writer.writeVarShort(this.reinitDoneQuestsIds.get(_loc5_));
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.finishedQuestsIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.finishedQuestsIds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.finishedQuestsCounts = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.finishedQuestsCounts.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.activeQuests = new ArrayList<QuestActiveInformations>();
			while( _loc7_ <  _loc6_){
				QuestActiveInformations _loc17_ = (QuestActiveInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc17_.Deserialize(reader);
				this.activeQuests.add(_loc17_);
				_loc7_++;
			}
			int _loc8_  = reader.readShort();
			int _loc9_  = 0;
			this.reinitDoneQuestsIds = new ArrayList<Integer>();
			while( _loc9_ <  _loc8_){
				int _loc18_ = reader.readVarShort();
				this.reinitDoneQuestsIds.add(_loc18_);
				_loc9_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
