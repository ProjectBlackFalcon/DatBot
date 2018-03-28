package protocol.network.messages.game.context.roleplay.objects;

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
public class ObjectGroundListAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 5925;

	private List<Integer> cells;
	private List<Integer> referenceIds;

	public List<Integer> getCells() { return this.cells; }
	public void setCells(List<Integer> cells) { this.cells = cells; };
	public List<Integer> getReferenceIds() { return this.referenceIds; }
	public void setReferenceIds(List<Integer> referenceIds) { this.referenceIds = referenceIds; };

	public ObjectGroundListAddedMessage(){
	}

	public ObjectGroundListAddedMessage(List<Integer> cells, List<Integer> referenceIds){
		this.cells = cells;
		this.referenceIds = referenceIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.cells.size());
			int _loc2_ = 0;
			while( _loc2_ < this.cells.size()){
				writer.writeVarShort(this.cells.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.referenceIds.size());
			int _loc3_ = 0;
			while( _loc3_ < this.referenceIds.size()){
				writer.writeVarShort(this.referenceIds.get(_loc3_));
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
			this.cells = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.cells.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.referenceIds = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.referenceIds.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
