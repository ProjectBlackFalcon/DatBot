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
import protocol.network.types.game.presets.ItemForPreset;

@SuppressWarnings("unused")
public class ItemForPresetUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6760;

	private int presetId;
	private ItemForPreset presetItem;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public ItemForPreset getPresetItem() { return this.presetItem; }
	public void setPresetItem(ItemForPreset presetItem) { this.presetItem = presetItem; };

	public ItemForPresetUpdateMessage(){
	}

	public ItemForPresetUpdateMessage(int presetId, ItemForPreset presetItem){
		this.presetId = presetId;
		this.presetItem = presetItem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.presetId);
			presetItem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readShort();
			this.presetItem = new ItemForPreset();
			this.presetItem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
