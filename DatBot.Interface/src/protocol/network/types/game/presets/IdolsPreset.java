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
public class IdolsPreset extends Preset {
	public static final int ProtocolId = 491;

	private int iconId;
	private List<Integer> idolIds;

	public int getIconId() { return this.iconId; }
	public void setIconId(int iconId) { this.iconId = iconId; };
	public List<Integer> getIdolIds() { return this.idolIds; }
	public void setIdolIds(List<Integer> idolIds) { this.idolIds = idolIds; };

	public IdolsPreset(){
	}

	public IdolsPreset(int iconId, List<Integer> idolIds){
		this.iconId = iconId;
		this.idolIds = idolIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.iconId);
			writer.writeShort(this.idolIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.idolIds.size()){
				writer.writeVarShort(this.idolIds.get(_loc2_));
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
			this.iconId = reader.readShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.idolIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.idolIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
