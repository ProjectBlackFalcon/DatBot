package protocol.network.types.game.presets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.presets.PresetsContainerPreset;

@SuppressWarnings("unused")
public class CharacterBuildPreset extends PresetsContainerPreset {
	public static final int ProtocolId = 534;

	private int iconId;
	private String name;

	public int getIconId() { return this.iconId; }
	public void setIconId(int iconId) { this.iconId = iconId; };
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };

	public CharacterBuildPreset(){
	}

	public CharacterBuildPreset(int iconId, String name){
		this.iconId = iconId;
		this.name = name;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.iconId);
			writer.writeUTF(this.name);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.iconId = reader.readShort();
			this.name = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
