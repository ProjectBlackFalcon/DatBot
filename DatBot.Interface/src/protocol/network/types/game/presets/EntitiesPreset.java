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
public class EntitiesPreset extends Preset {
	public static final int ProtocolId = 545;

	private int iconId;
	private List<Integer> entityIds;

	public int getIconId() { return this.iconId; }
	public void setIconId(int iconId) { this.iconId = iconId; };
	public List<Integer> getEntityIds() { return this.entityIds; }
	public void setEntityIds(List<Integer> entityIds) { this.entityIds = entityIds; };

	public EntitiesPreset(){
	}

	public EntitiesPreset(int iconId, List<Integer> entityIds){
		this.iconId = iconId;
		this.entityIds = entityIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.iconId);
			writer.writeShort(this.entityIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.entityIds.size()){
				writer.writeVarShort(this.entityIds.get(_loc2_));
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
			this.entityIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.entityIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
