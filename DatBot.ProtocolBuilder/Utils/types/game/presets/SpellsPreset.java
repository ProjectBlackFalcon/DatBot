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

@SuppressWarnings("unused")
public class SpellsPreset extends Preset {
	public static final int ProtocolId = 519;

	private List<Integer> spellIds;

	public List<Integer> getSpellIds() { return this.spellIds; }
	public void setSpellIds(List<Integer> spellIds) { this.spellIds = spellIds; };

	public SpellsPreset(){
	}

	public SpellsPreset(List<Integer> spellIds){
		this.spellIds = spellIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.spellIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.spellIds.size()){
				writer.writeVarShort(this.spellIds.get(_loc2_));
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
			this.spellIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.spellIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
