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
public class PresetUseResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6747;

	private int presetId;
	private int code;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public int getCode() { return this.code; }
	public void setCode(int code) { this.code = code; };

	public PresetUseResultMessage(){
	}

	public PresetUseResultMessage(int presetId, int code){
		this.presetId = presetId;
		this.code = code;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.presetId);
			writer.writeByte(this.code);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readShort();
			this.code = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
