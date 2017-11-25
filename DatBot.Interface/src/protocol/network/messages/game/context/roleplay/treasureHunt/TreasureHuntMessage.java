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

	public int questType;
	public double startMapId;
	public List<TreasureHuntStep> knownStepsList;
	public int totalStepCount;
	public int checkPointCurrent;
	public int checkPointTotal;
	public int availableRetryCount;
	public List<TreasureHuntFlag> flags;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("questType : " + this.questType);
		//Network.appendDebug("startMapId : " + this.startMapId);
		//for(TreasureHuntStep a : knownStepsList) {
			//Network.appendDebug("knownStepsList : " + a);
		//}
		//Network.appendDebug("totalStepCount : " + this.totalStepCount);
		//Network.appendDebug("checkPointCurrent : " + this.checkPointCurrent);
		//Network.appendDebug("checkPointTotal : " + this.checkPointTotal);
		//Network.appendDebug("availableRetryCount : " + this.availableRetryCount);
		//for(TreasureHuntFlag a : flags) {
			//Network.appendDebug("flags : " + a);
		//}
	//}
}