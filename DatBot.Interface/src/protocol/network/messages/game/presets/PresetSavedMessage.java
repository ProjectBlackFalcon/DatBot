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
import protocol.network.types.game.presets.Preset;

@SuppressWarnings("unused")
public class PresetSavedMessage extends NetworkMessage {
	public static final int ProtocolId = 6763;

	private int presetId;
	private Preset preset;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public Preset getPreset() { return this.preset; }
	public void setPreset(Preset preset) { this.preset = preset; };

	public PresetSavedMessage(){
	}

	public PresetSavedMessage(int presetId, Preset preset){
		this.presetId = presetId;
		this.preset = preset;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.presetId);
			writer.writeShort(Preset.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readShort();
			this.preset = (Preset) ProtocolTypeManager.getInstance(reader.readShort());
			this.preset.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
