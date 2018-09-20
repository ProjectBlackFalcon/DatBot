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

@SuppressWarnings("unused")
public class PresetDeleteRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6755;

	private int presetId;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };

	public PresetDeleteRequestMessage(){
	}

	public PresetDeleteRequestMessage(int presetId){
		this.presetId = presetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.presetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
