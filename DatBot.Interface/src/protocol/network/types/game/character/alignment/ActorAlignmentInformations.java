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

	public int alignmentSide;
	public int alignmentValue;
	public int alignmentGrade;
	public double characterPower;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("alignmentSide : " + this.alignmentSide);
		//Network.appendDebug("alignmentValue : " + this.alignmentValue);
		//Network.appendDebug("alignmentGrade : " + this.alignmentGrade);
		//Network.appendDebug("characterPower : " + this.characterPower);
	//}
}
