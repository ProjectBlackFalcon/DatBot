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
import protocol.network.types.game.presets.CharacterCharacteristicForPreset;

@SuppressWarnings("unused")
public class FullStatsPreset extends Preset {
	public static final int ProtocolId = 532;

	private List<CharacterCharacteristicForPreset> stats;

	public List<CharacterCharacteristicForPreset> getStats() { return this.stats; }
	public void setStats(List<CharacterCharacteristicForPreset> stats) { this.stats = stats; };

	public FullStatsPreset(){
	}

	public FullStatsPreset(List<CharacterCharacteristicForPreset> stats){
		this.stats = stats;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.stats.size());
			int _loc2_ = 0;
			while( _loc2_ < this.stats.size()){
				this.stats.get(_loc2_).Serialize(writer);
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
			this.stats = new ArrayList<CharacterCharacteristicForPreset>();
			while( _loc3_ <  _loc2_){
				CharacterCharacteristicForPreset _loc15_ = new CharacterCharacteristicForPreset();
				_loc15_.Deserialize(reader);
				this.stats.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
