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
import protocol.network.types.game.inventory.preset.Preset;

@SuppressWarnings("unused")
public class InventoryPresetUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6171;

	private Preset preset;

	public Preset getPreset() { return this.preset; };
	public void setPreset(Preset preset) { this.preset = preset; };

	public InventoryPresetUpdateMessage(){
	}

	public InventoryPresetUpdateMessage(Preset preset){
		this.preset = preset;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			preset.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.preset = new Preset();
			this.preset.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
