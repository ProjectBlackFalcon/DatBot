package protocol.network.messages.game.context.roleplay.havenbag;

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
public class HavenBagFurnituresRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6637;

	private List<Integer> cellIds;
	private List<Integer> funitureIds;
	private List<Integer> orientations;

	public List<Integer> getCellIds() { return this.cellIds; }
	public void setCellIds(List<Integer> cellIds) { this.cellIds = cellIds; };
	public List<Integer> getFunitureIds() { return this.funitureIds; }
	public void setFunitureIds(List<Integer> funitureIds) { this.funitureIds = funitureIds; };
	public List<Integer> getOrientations() { return this.orientations; }
	public void setOrientations(List<Integer> orientations) { this.orientations = orientations; };

	public HavenBagFurnituresRequestMessage(){
	}

	public HavenBagFurnituresRequestMessage(List<Integer> cellIds, List<Integer> funitureIds, List<Integer> orientations){
		this.cellIds = cellIds;
		this.funitureIds = funitureIds;
		this.orientations = orientations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.cellIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.cellIds.size()){
				writer.writeVarShort(this.cellIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.funitureIds.size());
			int _loc3_ = 0;
			while( _loc3_ < this.funitureIds.size()){
				writer.writeInt(this.funitureIds.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.orientations.size());
			int _loc4_ = 0;
			while( _loc4_ < this.orientations.size()){
				writer.writeByte(this.orientations.get(_loc4_));
				_loc4_++;
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
			this.cellIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.cellIds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.funitureIds = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readInt();
				this.funitureIds.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.orientations = new ArrayList<Integer>();
			while( _loc7_ <  _loc6_){
				int _loc17_ = reader.readByte();
				this.orientations.add(_loc17_);
				_loc7_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
