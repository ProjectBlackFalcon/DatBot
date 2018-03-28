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
import protocol.network.NetworkMessage;
import protocol.network.types.game.presets.Preset;

@SuppressWarnings("unused")
public class PresetsMessage extends NetworkMessage {
	public static final int ProtocolId = 6750;

	private List<Preset> presets;

	public List<Preset> getPresets() { return this.presets; }
	public void setPresets(List<Preset> presets) { this.presets = presets; };

	public PresetsMessage(){
	}

	public PresetsMessage(List<Preset> presets){
		this.presets = presets;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.presets.size());
			int _loc2_ = 0;
			while( _loc2_ < this.presets.size()){
				writer.writeShort(Preset.ProtocolId);
				this.presets.get(_loc2_).Serialize(writer);
				_loc2_++;
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
			this.presets = new ArrayList<Preset>();
			while( _loc3_ <  _loc2_){
				Preset _loc15_ = (Preset) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.presets.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
