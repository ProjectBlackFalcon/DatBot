package protocol.network.types.game.achievement;

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
import protocol.network.types.game.achievement.AchievementObjective;
import protocol.network.types.game.achievement.AchievementStartedObjective;

@SuppressWarnings("unused")
public class Achievement extends NetworkMessage {
	public static final int ProtocolId = 363;

	private int id;
	private List<AchievementObjective> finishedObjective;
	private List<AchievementStartedObjective> startedObjectives;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public List<AchievementObjective> getFinishedObjective() { return this.finishedObjective; }
	public void setFinishedObjective(List<AchievementObjective> finishedObjective) { this.finishedObjective = finishedObjective; };
	public List<AchievementStartedObjective> getStartedObjectives() { return this.startedObjectives; }
	public void setStartedObjectives(List<AchievementStartedObjective> startedObjectives) { this.startedObjectives = startedObjectives; };

	public Achievement(){
	}

	public Achievement(int id, List<AchievementObjective> finishedObjective, List<AchievementStartedObjective> startedObjectives){
		this.id = id;
		this.finishedObjective = finishedObjective;
		this.startedObjectives = startedObjectives;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeShort(this.finishedObjective.size());
			int _loc2_ = 0;
			while( _loc2_ < this.finishedObjective.size()){
				this.finishedObjective.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.startedObjectives.size());
			int _loc3_ = 0;
			while( _loc3_ < this.startedObjectives.size()){
				this.startedObjectives.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.finishedObjective = new ArrayList<AchievementObjective>();
			while( _loc3_ <  _loc2_){
				AchievementObjective _loc15_ = new AchievementObjective();
				_loc15_.Deserialize(reader);
				this.finishedObjective.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.startedObjectives = new ArrayList<AchievementStartedObjective>();
			while( _loc5_ <  _loc4_){
				AchievementStartedObjective _loc16_ = new AchievementStartedObjective();
				_loc16_.Deserialize(reader);
				this.startedObjectives.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
