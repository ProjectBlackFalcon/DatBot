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
public class PresetUseRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6759;

	private int presetId;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };

	public PresetUseRequestMessage(){
	}

	public PresetUseRequestMessage(int presetId){
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
