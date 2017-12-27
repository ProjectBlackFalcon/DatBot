package protocol.network.types.game.context.roleplay.job;

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
public class DecraftedItemStackInfo extends NetworkMessage {
	public static final int ProtocolId = 481;

	private int objectUID;
	private double bonusMin;
	private double bonusMax;
	private List<Integer> runesId;
	private List<Integer> runesQty;

	public int getObjectUID() { return this.objectUID; };
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };
	public double getBonusMin() { return this.bonusMin; };
	public void setBonusMin(double bonusMin) { this.bonusMin = bonusMin; };
	public double getBonusMax() { return this.bonusMax; };
	public void setBonusMax(double bonusMax) { this.bonusMax = bonusMax; };
	public List<Integer> getRunesId() { return this.runesId; };
	public void setRunesId(List<Integer> runesId) { this.runesId = runesId; };
	public List<Integer> getRunesQty() { return this.runesQty; };
	public void setRunesQty(List<Integer> runesQty) { this.runesQty = runesQty; };

	public DecraftedItemStackInfo(){
	}

	public DecraftedItemStackInfo(int objectUID, double bonusMin, double bonusMax, List<Integer> runesId, List<Integer> runesQty){
		this.objectUID = objectUID;
		this.bonusMin = bonusMin;
		this.bonusMax = bonusMax;
		this.runesId = runesId;
		this.runesQty = runesQty;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
			writer.writeDouble(this.bonusMin);
			writer.writeDouble(this.bonusMax);
			writer.writeShort(this.runesId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.runesId.size()){
				writer.writeVarShort(this.runesId.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.runesQty.size());
			int _loc3_ = 0;
			while( _loc3_ < this.runesQty.size()){
				writer.writeVarInt(this.runesQty.get(_loc3_));
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectUID = reader.readVarInt();
			this.bonusMin = reader.readDouble();
			this.bonusMax = reader.readDouble();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.runesId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.runesId.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.runesQty = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarInt();
				this.runesQty.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
