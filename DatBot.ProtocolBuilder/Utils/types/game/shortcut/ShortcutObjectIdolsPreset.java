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
import protocol.network.types.game.shortcut.ShortcutObject;

@SuppressWarnings("unused")
public class ShortcutObjectIdolsPreset extends ShortcutObject {
	public static final int ProtocolId = 492;

	private int presetId;

	public int getPresetId() { return this.presetId; }
	public void setPresetId(int presetId) { this.presetId = presetId; };

	public ShortcutObjectIdolsPreset(){
	}

	public ShortcutObjectIdolsPreset(int presetId){
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
