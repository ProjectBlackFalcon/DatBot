package protocol.network.messages.game.interactive.zaap;

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
public class TeleportDestinationsListMessage extends NetworkMessage {
	public static final int ProtocolId = 5960;

	private int teleporterType;
	private List<Double> mapIds;
	private List<Integer> subAreaIds;
	private List<Integer> costs;
	private List<Integer> destTeleporterType;

	public int getTeleporterType() { return this.teleporterType; }
	public void setTeleporterType(int teleporterType) { this.teleporterType = teleporterType; };
	public List<Double> getMapIds() { return this.mapIds; }
	public void setMapIds(List<Double> mapIds) { this.mapIds = mapIds; };
	public List<Integer> getSubAreaIds() { return this.subAreaIds; }
	public void setSubAreaIds(List<Integer> subAreaIds) { this.subAreaIds = subAreaIds; };
	public List<Integer> getCosts() { return this.costs; }
	public void setCosts(List<Integer> costs) { this.costs = costs; };
	public List<Integer> getDestTeleporterType() { return this.destTeleporterType; }
	public void setDestTeleporterType(List<Integer> destTeleporterType) { this.destTeleporterType = destTeleporterType; };

	public TeleportDestinationsListMessage(){
	}

	public TeleportDestinationsListMessage(int teleporterType, List<Double> mapIds, List<Integer> subAreaIds, List<Integer> costs, List<Integer> destTeleporterType){
		this.teleporterType = teleporterType;
		this.mapIds = mapIds;
		this.subAreaIds = subAreaIds;
		this.costs = costs;
		this.destTeleporterType = destTeleporterType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.teleporterType);
			writer.writeShort(this.mapIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.mapIds.size()){
				writer.writeDouble(this.mapIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.subAreaIds.size());
			int _loc3_ = 0;
			while( _loc3_ < this.subAreaIds.size()){
				writer.writeVarShort(this.subAreaIds.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.costs.size());
			int _loc4_ = 0;
			while( _loc4_ < this.costs.size()){
				writer.writeVarShort(this.costs.get(_loc4_));
				_loc4_++;
			}
			writer.writeShort(this.destTeleporterType.size());
			int _loc5_ = 0;
			while( _loc5_ < this.destTeleporterType.size()){
				writer.writeByte(this.destTeleporterType.get(_loc5_));
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.teleporterType = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.mapIds = new ArrayList<Double>();
			while( _loc3_ <  _loc2_){
				double _loc15_ = reader.readDouble();
				this.mapIds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.subAreaIds = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.subAreaIds.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.costs = new ArrayList<Integer>();
			while( _loc7_ <  _loc6_){
				int _loc17_ = reader.readVarShort();
				this.costs.add(_loc17_);
				_loc7_++;
			}
			int _loc8_  = reader.readShort();
			int _loc9_  = 0;
			this.destTeleporterType = new ArrayList<Integer>();
			while( _loc9_ <  _loc8_){
				int _loc18_ = reader.readByte();
				this.destTeleporterType.add(_loc18_);
				_loc9_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
