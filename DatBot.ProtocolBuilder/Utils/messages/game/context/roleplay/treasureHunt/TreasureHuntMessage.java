package protocol.network.messages.game.context.roleplay.treasureHunt;

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
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntStep;
import protocol.network.types.game.context.roleplay.treasureHunt.TreasureHuntFlag;

@SuppressWarnings("unused")
public class TreasureHuntMessage extends NetworkMessage {
	public static final int ProtocolId = 6486;

	private int questType;
	private double startMapId;
	private List<TreasureHuntStep> knownStepsList;
	private int totalStepCount;
	private int checkPointCurrent;
	private int checkPointTotal;
	private int availableRetryCount;
	private List<TreasureHuntFlag> flags;

	public int getQuestType() { return this.questType; }
	public void setQuestType(int questType) { this.questType = questType; };
	public double getStartMapId() { return this.startMapId; }
	public void setStartMapId(double startMapId) { this.startMapId = startMapId; };
	public List<TreasureHuntStep> getKnownStepsList() { return this.knownStepsList; }
	public void setKnownStepsList(List<TreasureHuntStep> knownStepsList) { this.knownStepsList = knownStepsList; };
	public int getTotalStepCount() { return this.totalStepCount; }
	public void setTotalStepCount(int totalStepCount) { this.totalStepCount = totalStepCount; };
	public int getCheckPointCurrent() { return this.checkPointCurrent; }
	public void setCheckPointCurrent(int checkPointCurrent) { this.checkPointCurrent = checkPointCurrent; };
	public int getCheckPointTotal() { return this.checkPointTotal; }
	public void setCheckPointTotal(int checkPointTotal) { this.checkPointTotal = checkPointTotal; };
	public int getAvailableRetryCount() { return this.availableRetryCount; }
	public void setAvailableRetryCount(int availableRetryCount) { this.availableRetryCount = availableRetryCount; };
	public List<TreasureHuntFlag> getFlags() { return this.flags; }
	public void setFlags(List<TreasureHuntFlag> flags) { this.flags = flags; };

	public TreasureHuntMessage(){
	}

	public TreasureHuntMessage(int questType, double startMapId, List<TreasureHuntStep> knownStepsList, int totalStepCount, int checkPointCurrent, int checkPointTotal, int availableRetryCount, List<TreasureHuntFlag> flags){
		this.questType = questType;
		this.startMapId = startMapId;
		this.knownStepsList = knownStepsList;
		this.totalStepCount = totalStepCount;
		this.checkPointCurrent = checkPointCurrent;
		this.checkPointTotal = checkPointTotal;
		this.availableRetryCount = availableRetryCount;
		this.flags = flags;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.questType);
			writer.writeDouble(this.startMapId);
			writer.writeShort(this.knownStepsList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.knownStepsList.size()){
				writer.writeShort(TreasureHuntStep.ProtocolId);
				this.knownStepsList.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeByte(this.totalStepCount);
			writer.writeVarInt(this.checkPointCurrent);
			writer.writeVarInt(this.checkPointTotal);
			writer.writeInt(this.availableRetryCount);
			writer.writeShort(this.flags.size());
			int _loc3_ = 0;
			while( _loc3_ < this.flags.size()){
				this.flags.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questType = reader.readByte();
			this.startMapId = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.knownStepsList = new ArrayList<TreasureHuntStep>();
			while( _loc3_ <  _loc2_){
				TreasureHuntStep _loc15_ = (TreasureHuntStep) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.knownStepsList.add(_loc15_);
				_loc3_++;
			}
			this.totalStepCount = reader.readByte();
			this.checkPointCurrent = reader.readVarInt();
			this.checkPointTotal = reader.readVarInt();
			this.availableRetryCount = reader.readInt();
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.flags = new ArrayList<TreasureHuntFlag>();
			while( _loc5_ <  _loc4_){
				TreasureHuntFlag _loc16_ = new TreasureHuntFlag();
				_loc16_.Deserialize(reader);
				this.flags.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
