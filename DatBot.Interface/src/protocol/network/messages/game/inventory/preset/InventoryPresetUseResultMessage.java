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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class InventoryPresetUseResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6163;

	private int presetId;
	private int code;
	private List<Integer> unlinkedPosition;

	public int getPresetId() { return this.presetId; };
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public int getCode() { return this.code; };
	public void setCode(int code) { this.code = code; };
	public List<Integer> getUnlinkedPosition() { return this.unlinkedPosition; };
	public void setUnlinkedPosition(List<Integer> unlinkedPosition) { this.unlinkedPosition = unlinkedPosition; };

	public InventoryPresetUseResultMessage(){
	}

	public InventoryPresetUseResultMessage(int presetId, int code, List<Integer> unlinkedPosition){
		this.presetId = presetId;
		this.code = code;
		this.unlinkedPosition = unlinkedPosition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeByte(this.code);
			writer.writeShort(this.unlinkedPosition.size());
			int _loc2_ = 0;
			while( _loc2_ < this.unlinkedPosition.size()){
				writer.writeByte(this.unlinkedPosition.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.code = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.unlinkedPosition = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.unlinkedPosition.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
