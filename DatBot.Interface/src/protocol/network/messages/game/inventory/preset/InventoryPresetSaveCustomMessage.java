package protocol.network.messages.game.inventory.preset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.AbstractPresetSaveMessage;

@SuppressWarnings("unused")
public class InventoryPresetSaveCustomMessage extends AbstractPresetSaveMessage {
	public static final int ProtocolId = 6329;

	public List<Integer> itemsPositions;
	public List<Integer> itemsUids;

	public InventoryPresetSaveCustomMessage(){
	}

	public InventoryPresetSaveCustomMessage(List<Integer> itemsPositions, List<Integer> itemsUids){
		this.itemsPositions = itemsPositions;
		this.itemsUids = itemsUids;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.itemsPositions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.itemsPositions.size()){
				writer.writeByte(this.itemsPositions.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.itemsUids.size());
			int _loc3_ = 0;
			while( _loc3_ < this.itemsUids.size()){
				writer.writeVarInt(this.itemsUids.get(_loc3_));
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.itemsPositions = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.itemsPositions.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.itemsUids = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarInt();
				this.itemsUids.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : itemsPositions) {
			//Network.appendDebug("itemsPositions : " + a);
		//}
		//for(Integer a : itemsUids) {
			//Network.appendDebug("itemsUids : " + a);
		//}
	//}
}
