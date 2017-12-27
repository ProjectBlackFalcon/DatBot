package protocol.network.types.game.character.characteristic;

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
import protocol.network.types.game.character.characteristic.CharacterBaseCharacteristic;

@SuppressWarnings("unused")
public class CharacterSpellModification extends NetworkMessage {
	public static final int ProtocolId = 215;

	private int modificationType;
	private int spellId;
	private CharacterBaseCharacteristic value;

	public int getModificationType() { return this.modificationType; };
	public void setModificationType(int modificationType) { this.modificationType = modificationType; };
	public int getSpellId() { return this.spellId; };
	public void setSpellId(int spellId) { this.spellId = spellId; };
	public CharacterBaseCharacteristic getValue() { return this.value; };
	public void setValue(CharacterBaseCharacteristic value) { this.value = value; };

	public CharacterSpellModification(){
	}

	public CharacterSpellModification(int modificationType, int spellId, CharacterBaseCharacteristic value){
		this.modificationType = modificationType;
		this.spellId = spellId;
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.modificationType);
			writer.writeVarShort(this.spellId);
			value.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.modificationType = reader.readByte();
			this.spellId = reader.readVarShort();
			this.value = new CharacterBaseCharacteristic();
			this.value.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
