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
import protocol.network.messages.game.presets.PresetSaveRequestMessage;

@SuppressWarnings("unused")
public class CharacterPresetSaveRequestMessage extends PresetSaveRequestMessage {
	public static final int ProtocolId = 6756;

	private String name;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };

	public CharacterPresetSaveRequestMessage(){
	}

	public CharacterPresetSaveRequestMessage(String name){
		this.name = name;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.name);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.name = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
