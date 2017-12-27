package protocol.network.messages.game.inventory.preset;

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
import protocol.network.types.game.inventory.preset.PresetItem;

@SuppressWarnings("unused")
public class InventoryPresetItemUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6168;

	private int presetId;
	private PresetItem presetItem;

	public int getPresetId() { return this.presetId; };
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public PresetItem getPresetItem() { return this.presetItem; };
	public void setPresetItem(PresetItem presetItem) { this.presetItem = presetItem; };

	public InventoryPresetItemUpdateMessage(){
	}

	public InventoryPresetItemUpdateMessage(int presetId, PresetItem presetItem){
		this.presetId = presetId;
		this.presetItem = presetItem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			presetItem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.presetItem = new PresetItem();
			this.presetItem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
