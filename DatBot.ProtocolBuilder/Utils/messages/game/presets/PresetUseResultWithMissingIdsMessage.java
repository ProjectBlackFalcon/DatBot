package protocol.network.messages.game.presets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.presets.PresetUseResultMessage;

@SuppressWarnings("unused")
public class PresetUseResultWithMissingIdsMessage extends PresetUseResultMessage {
	public static final int ProtocolId = 6757;

	private List<Integer> missingIds;

	public List<Integer> getMissingIds() { return this.missingIds; }
	public void setMissingIds(List<Integer> missingIds) { this.missingIds = missingIds; };

	public PresetUseResultWithMissingIdsMessage(){
	}

	public PresetUseResultWithMissingIdsMessage(List<Integer> missingIds){
		this.missingIds = missingIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.missingIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.missingIds.size()){
				writer.writeVarShort(this.missingIds.get(_loc2_));
				_loc2_++;
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
			this.missingIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.missingIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
