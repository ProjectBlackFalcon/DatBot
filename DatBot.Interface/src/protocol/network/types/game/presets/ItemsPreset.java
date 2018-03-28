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
import protocol.network.types.game.presets.ItemForPreset;
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class ItemsPreset extends Preset {
	public static final int ProtocolId = 517;

	private List<ItemForPreset> items;
	private boolean mountEquipped;
	private EntityLook look;

	public List<ItemForPreset> getItems() { return this.items; }
	public void setItems(List<ItemForPreset> items) { this.items = items; };
	public boolean isMountEquipped() { return this.mountEquipped; }
	public void setMountEquipped(boolean mountEquipped) { this.mountEquipped = mountEquipped; };
	public EntityLook getLook() { return this.look; }
	public void setLook(EntityLook look) { this.look = look; };

	public ItemsPreset(){
	}

	public ItemsPreset(List<ItemForPreset> items, boolean mountEquipped, EntityLook look){
		this.items = items;
		this.mountEquipped = mountEquipped;
		this.look = look;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.items.size());
			int _loc2_ = 0;
			while( _loc2_ < this.items.size()){
				this.items.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeBoolean(this.mountEquipped);
			look.Serialize(writer);
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
			this.items = new ArrayList<ItemForPreset>();
			while( _loc3_ <  _loc2_){
				ItemForPreset _loc15_ = new ItemForPreset();
				_loc15_.Deserialize(reader);
				this.items.add(_loc15_);
				_loc3_++;
			}
			this.mountEquipped = reader.readBoolean();
			this.look = new EntityLook();
			this.look.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
