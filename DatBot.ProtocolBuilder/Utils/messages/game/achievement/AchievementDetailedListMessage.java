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
import protocol.network.types.game.achievement.Achievement;

@SuppressWarnings("unused")
public class AchievementDetailedListMessage extends NetworkMessage {
	public static final int ProtocolId = 6358;

	private List<Achievement> startedAchievements;
	private List<Achievement> finishedAchievements;

	public List<Achievement> getStartedAchievements() { return this.startedAchievements; }
	public void setStartedAchievements(List<Achievement> startedAchievements) { this.startedAchievements = startedAchievements; };
	public List<Achievement> getFinishedAchievements() { return this.finishedAchievements; }
	public void setFinishedAchievements(List<Achievement> finishedAchievements) { this.finishedAchievements = finishedAchievements; };

	public AchievementDetailedListMessage(){
	}

	public AchievementDetailedListMessage(List<Achievement> startedAchievements, List<Achievement> finishedAchievements){
		this.startedAchievements = startedAchievements;
		this.finishedAchievements = finishedAchievements;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.startedAchievements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.startedAchievements.size()){
				this.startedAchievements.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.finishedAchievements.size());
			int _loc3_ = 0;
			while( _loc3_ < this.finishedAchievements.size()){
				this.finishedAchievements.get(_loc3_).Serialize(writer);
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
			this.startedAchievements = new ArrayList<Achievement>();
			while( _loc3_ <  _loc2_){
				Achievement _loc15_ = new Achievement();
				_loc15_.Deserialize(reader);
				this.startedAchievements.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.finishedAchievements = new ArrayList<Achievement>();
			while( _loc5_ <  _loc4_){
				Achievement _loc16_ = new Achievement();
				_loc16_.Deserialize(reader);
				this.finishedAchievements.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
