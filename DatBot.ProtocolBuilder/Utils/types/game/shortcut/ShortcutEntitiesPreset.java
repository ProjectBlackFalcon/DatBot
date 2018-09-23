package protocol.network.types.game.shortcut;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.shortcut.Shortcut;

@SuppressWarnings("unused")
public class ShortcutEntitiesPreset extends Shortcut {
	public static final int ProtocolId = 544;

	private int presetId;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };

	public ShortcutEntitiesPreset(){
	}

	public ShortcutEntitiesPreset(int presetId){
		this.presetId = presetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.presetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.presetId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
