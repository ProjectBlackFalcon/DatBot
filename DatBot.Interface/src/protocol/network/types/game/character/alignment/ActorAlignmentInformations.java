package protocol.network.types.game.character.alignment;

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
public class ActorAlignmentInformations extends NetworkMessage {
	public static final int ProtocolId = 201;

	private int alignmentSide;
	private int alignmentValue;
	private int alignmentGrade;
	private double characterPower;

	public int getAlignmentSide() { return this.alignmentSide; }
	public void setAlignmentSide(int alignmentSide) { this.alignmentSide = alignmentSide; };
	public int getAlignmentValue() { return this.alignmentValue; }
	public void setAlignmentValue(int alignmentValue) { this.alignmentValue = alignmentValue; };
	public int getAlignmentGrade() { return this.alignmentGrade; }
	public void setAlignmentGrade(int alignmentGrade) { this.alignmentGrade = alignmentGrade; };
	public double getCharacterPower() { return this.characterPower; }
	public void setCharacterPower(double characterPower) { this.characterPower = characterPower; };

	public ActorAlignmentInformations(){
	}

	public ActorAlignmentInformations(int alignmentSide, int alignmentValue, int alignmentGrade, double characterPower){
		this.alignmentSide = alignmentSide;
		this.alignmentValue = alignmentValue;
		this.alignmentGrade = alignmentGrade;
		this.characterPower = characterPower;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.alignmentSide);
			writer.writeByte(this.alignmentValue);
			writer.writeByte(this.alignmentGrade);
			writer.writeDouble(this.characterPower);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.alignmentSide = reader.readByte();
			this.alignmentValue = reader.readByte();
			this.alignmentGrade = reader.readByte();
			this.characterPower = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
