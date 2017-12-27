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
import protocol.network.messages.game.inventory.AbstractPresetSaveMessage;

@SuppressWarnings("unused")
public class InventoryPresetSaveMessage extends AbstractPresetSaveMessage {
	public static final int ProtocolId = 6165;

	private boolean saveEquipment;

	public boolean isSaveEquipment() { return this.saveEquipment; };
	public void setSaveEquipment(boolean saveEquipment) { this.saveEquipment = saveEquipment; };

	public InventoryPresetSaveMessage(){
	}

	public InventoryPresetSaveMessage(boolean saveEquipment){
		this.saveEquipment = saveEquipment;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.saveEquipment);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.saveEquipment = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
