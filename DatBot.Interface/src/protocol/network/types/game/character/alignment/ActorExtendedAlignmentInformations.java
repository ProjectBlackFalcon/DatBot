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
import protocol.network.types.game.character.alignment.ActorAlignmentInformations;

@SuppressWarnings("unused")
public class ActorExtendedAlignmentInformations extends ActorAlignmentInformations {
	public static final int ProtocolId = 202;

	public int honor;
	public int honorGradeFloor;
	public int honorNextGradeFloor;
	public int aggressable;

	public ActorExtendedAlignmentInformations(){
	}

	public ActorExtendedAlignmentInformations(int honor, int honorGradeFloor, int honorNextGradeFloor, int aggressable){
		this.honor = honor;
		this.honorGradeFloor = honorGradeFloor;
		this.honorNextGradeFloor = honorNextGradeFloor;
		this.aggressable = aggressable;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.honor);
			writer.writeVarShort(this.honorGradeFloor);
			writer.writeVarShort(this.honorNextGradeFloor);
			writer.writeByte(this.aggressable);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.honor = reader.readVarShort();
			this.honorGradeFloor = reader.readVarShort();
			this.honorNextGradeFloor = reader.readVarShort();
			this.aggressable = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("honor : " + this.honor);
		//Network.appendDebug("honorGradeFloor : " + this.honorGradeFloor);
		//Network.appendDebug("honorNextGradeFloor : " + this.honorNextGradeFloor);
		//Network.appendDebug("aggressable : " + this.aggressable);
	//}
}
