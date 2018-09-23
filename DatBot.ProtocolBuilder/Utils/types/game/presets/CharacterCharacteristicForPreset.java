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
import protocol.network.types.game.presets.SimpleCharacterCharacteristicForPreset;

@SuppressWarnings("unused")
public class CharacterCharacteristicForPreset extends SimpleCharacterCharacteristicForPreset {
	public static final int ProtocolId = 539;

	private int stuff;

	public int getStuff() { return this.stuff; }
	public void setStuff(int stuff) { this.stuff = stuff; };

	public CharacterCharacteristicForPreset(){
	}

	public CharacterCharacteristicForPreset(int stuff){
		this.stuff = stuff;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.stuff);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.stuff = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
