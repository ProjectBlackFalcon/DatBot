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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class GameRolePlayNpcQuestFlag extends NetworkMessage {
	public static final int ProtocolId = 384;

	private List<Integer> questsToValidId;
	private List<Integer> questsToStartId;

	public List<Integer> getQuestsToValidId() { return this.questsToValidId; }
	public void setQuestsToValidId(List<Integer> questsToValidId) { this.questsToValidId = questsToValidId; };
	public List<Integer> getQuestsToStartId() { return this.questsToStartId; }
	public void setQuestsToStartId(List<Integer> questsToStartId) { this.questsToStartId = questsToStartId; };

	public GameRolePlayNpcQuestFlag(){
	}

	public GameRolePlayNpcQuestFlag(List<Integer> questsToValidId, List<Integer> questsToStartId){
		this.questsToValidId = questsToValidId;
		this.questsToStartId = questsToStartId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.questsToValidId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.questsToValidId.size()){
				writer.writeVarShort(this.questsToValidId.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.questsToStartId.size());
			int _loc3_ = 0;
			while( _loc3_ < this.questsToStartId.size()){
				writer.writeVarShort(this.questsToStartId.get(_loc3_));
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
			this.questsToValidId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.questsToValidId.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.questsToStartId = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.questsToStartId.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
