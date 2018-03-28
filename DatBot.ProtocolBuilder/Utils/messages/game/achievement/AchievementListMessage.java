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
import protocol.network.types.game.achievement.AchievementAchieved;

@SuppressWarnings("unused")
public class AchievementListMessage extends NetworkMessage {
	public static final int ProtocolId = 6205;

	private List<AchievementAchieved> finishedAchievements;

	public List<AchievementAchieved> getFinishedAchievements() { return this.finishedAchievements; }
	public void setFinishedAchievements(List<AchievementAchieved> finishedAchievements) { this.finishedAchievements = finishedAchievements; };

	public AchievementListMessage(){
	}

	public AchievementListMessage(List<AchievementAchieved> finishedAchievements){
		this.finishedAchievements = finishedAchievements;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.finishedAchievements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.finishedAchievements.size()){
				writer.writeShort(AchievementAchieved.ProtocolId);
				this.finishedAchievements.get(_loc2_).Serialize(writer);
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
			this.finishedAchievements = new ArrayList<AchievementAchieved>();
			while( _loc3_ <  _loc2_){
				AchievementAchieved _loc15_ = (AchievementAchieved) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.finishedAchievements.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
