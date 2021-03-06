package protocol.network.messages.game.inventory.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.items.InventoryContentMessage;
import protocol.network.types.game.inventory.preset.Preset;
import protocol.network.types.game.inventory.preset.IdolsPreset;

@SuppressWarnings("unused")
public class InventoryContentAndPresetMessage extends InventoryContentMessage {
	public static final int ProtocolId = 6162;

	private List<Preset> presets;
	private List<IdolsPreset> idolsPresets;

	public List<Preset> getPresets() { return this.presets; };
	public void setPresets(List<Preset> presets) { this.presets = presets; };
	public List<IdolsPreset> getIdolsPresets() { return this.idolsPresets; };
	public void setIdolsPresets(List<IdolsPreset> idolsPresets) { this.idolsPresets = idolsPresets; };

	public InventoryContentAndPresetMessage(){
	}

	public InventoryContentAndPresetMessage(List<Preset> presets, List<IdolsPreset> idolsPresets){
		this.presets = presets;
		this.idolsPresets = idolsPresets;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.presets.size());
			int _loc2_ = 0;
			while( _loc2_ < this.presets.size()){
				this.presets.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.idolsPresets.size());
			int _loc3_ = 0;
			while( _loc3_ < this.idolsPresets.size()){
				this.idolsPresets.get(_loc3_).Serialize(writer);
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
			this.presets = new ArrayList<Preset>();
			while( _loc3_ <  _loc2_){
				Preset _loc15_ = new Preset();
				_loc15_.Deserialize(reader);
				this.presets.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.idolsPresets = new ArrayList<IdolsPreset>();
			while( _loc5_ <  _loc4_){
				IdolsPreset _loc16_ = new IdolsPreset();
				_loc16_.Deserialize(reader);
				this.idolsPresets.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
