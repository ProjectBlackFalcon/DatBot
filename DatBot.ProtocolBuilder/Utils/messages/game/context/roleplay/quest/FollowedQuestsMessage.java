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
import protocol.network.types.game.context.roleplay.quest.QuestActiveDetailedInformations;

@SuppressWarnings("unused")
public class FollowedQuestsMessage extends NetworkMessage {
	public static final int ProtocolId = 6717;

	private List<QuestActiveDetailedInformations> quests;

	public List<QuestActiveDetailedInformations> getQuests() { return this.quests; }
	public void setQuests(List<QuestActiveDetailedInformations> quests) { this.quests = quests; };

	public FollowedQuestsMessage(){
	}

	public FollowedQuestsMessage(List<QuestActiveDetailedInformations> quests){
		this.quests = quests;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.quests.size());
			int _loc2_ = 0;
			while( _loc2_ < this.quests.size()){
				this.quests.get(_loc2_).Serialize(writer);
				_loc2_++;
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
			this.quests = new ArrayList<QuestActiveDetailedInformations>();
			while( _loc3_ <  _loc2_){
				QuestActiveDetailedInformations _loc15_ = new QuestActiveDetailedInformations();
				_loc15_.Deserialize(reader);
				this.quests.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
