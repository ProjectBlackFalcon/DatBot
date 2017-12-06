package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.FightResultAdditionalData;

@SuppressWarnings("unused")
public class FightResultPvpData extends FightResultAdditionalData {
	public static final int ProtocolId = 190;

	public int grade;
	public int minHonorForGrade;
	public int maxHonorForGrade;
	public int honor;
	public int honorDelta;

	public FightResultPvpData(){
	}

	public FightResultPvpData(int grade, int minHonorForGrade, int maxHonorForGrade, int honor, int honorDelta){
		this.grade = grade;
		this.minHonorForGrade = minHonorForGrade;
		this.maxHonorForGrade = maxHonorForGrade;
		this.honor = honor;
		this.honorDelta = honorDelta;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.grade);
			writer.writeVarShort(this.minHonorForGrade);
			writer.writeVarShort(this.maxHonorForGrade);
			writer.writeVarShort(this.honor);
			writer.writeVarShort(this.honorDelta);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.grade = reader.readByte();
			this.minHonorForGrade = reader.readVarShort();
			this.maxHonorForGrade = reader.readVarShort();
			this.honor = reader.readVarShort();
			this.honorDelta = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("grade : " + this.grade);
		//Network.appendDebug("minHonorForGrade : " + this.minHonorForGrade);
		//Network.appendDebug("maxHonorForGrade : " + this.maxHonorForGrade);
		//Network.appendDebug("honor : " + this.honor);
		//Network.appendDebug("honorDelta : " + this.honorDelta);
	//}
}
