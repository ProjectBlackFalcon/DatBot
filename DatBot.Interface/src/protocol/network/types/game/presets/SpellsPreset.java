package protocol.network.types.game.presets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.presets.Preset;
import protocol.network.types.game.presets.SpellForPreset;

@SuppressWarnings("unused")
public class SpellsPreset extends Preset {
	public static final int ProtocolId = 519;

	private List<SpellForPreset> spells;

	public List<SpellForPreset> getSpells() { return this.spells; }
	public void setSpells(List<SpellForPreset> spells) { this.spells = spells; };

	public SpellsPreset(){
	}

	public SpellsPreset(List<SpellForPreset> spells){
		this.spells = spells;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.spells.size());
			int _loc2_ = 0;
			while( _loc2_ < this.spells.size()){
				this.spells.get(_loc2_).Serialize(writer);
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
			this.spells = new ArrayList<SpellForPreset>();
			while( _loc3_ <  _loc2_){
				SpellForPreset _loc15_ = new SpellForPreset();
				_loc15_.Deserialize(reader);
				this.spells.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
