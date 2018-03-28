package protocol.network.types.game.character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.CharacterMinimalPlusLookInformations;

@SuppressWarnings("unused")
public class CharacterMinimalPlusLookAndGradeInformations extends CharacterMinimalPlusLookInformations {
	public static final int ProtocolId = 193;

	private int grade;

	public int getGrade() { return this.grade; }
	public void setGrade(int grade) { this.grade = grade; };

	public CharacterMinimalPlusLookAndGradeInformations(){
	}

	public CharacterMinimalPlusLookAndGradeInformations(int grade){
		this.grade = grade;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.grade);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.grade = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
