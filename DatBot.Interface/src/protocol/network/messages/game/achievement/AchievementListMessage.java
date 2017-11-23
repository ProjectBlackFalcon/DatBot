package protocol.network.messages.game.achievement;

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
import protocol.network.types.game.achievement.AchievementRewardable;

@SuppressWarnings("unused")
public class AchievementListMessage extends NetworkMessage {
	public static final int ProtocolId = 6205;

	public List<Integer> finishedAchievementsIds;
	public List<AchievementRewardable> rewardableAchievements;

	public AchievementListMessage(){
	}

	public AchievementListMessage(List<Integer> finishedAchievementsIds, List<AchievementRewardable> rewardableAchievements){
		this.finishedAchievementsIds = finishedAchievementsIds;
		this.rewardableAchievements = rewardableAchievements;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.finishedAchievementsIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.finishedAchievementsIds.size()){
				writer.writeVarShort(this.finishedAchievementsIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.rewardableAchievements.size());
			int _loc3_ = 0;
			while( _loc3_ < this.rewardableAchievements.size()){
				this.rewardableAchievements.get(_loc3_).Serialize(writer);
				_loc3_++;
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
			this.finishedAchievementsIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.finishedAchievementsIds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.rewardableAchievements = new ArrayList<AchievementRewardable>();
			while( _loc5_ <  _loc4_){
				AchievementRewardable _loc16_ = new AchievementRewardable();
				_loc16_.Deserialize(reader);
				this.rewardableAchievements.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : finishedAchievementsIds) {
			//Network.appendDebug("finishedAchievementsIds : " + a);
		//}
		//for(AchievementRewardable a : rewardableAchievements) {
			//Network.appendDebug("rewardableAchievements : " + a);
		//}
	//}
}
