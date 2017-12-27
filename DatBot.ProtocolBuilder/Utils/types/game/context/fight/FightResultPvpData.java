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

	private int grade;
	private int minHonorForGrade;
	private int maxHonorForGrade;
	private int honor;
	private int honorDelta;

	public int getGrade() { return this.grade; };
	public void setGrade(int grade) { this.grade = grade; };
	public int getMinHonorForGrade() { return this.minHonorForGrade; };
	public void setMinHonorForGrade(int minHonorForGrade) { this.minHonorForGrade = minHonorForGrade; };
	public int getMaxHonorForGrade() { return this.maxHonorForGrade; };
	public void setMaxHonorForGrade(int maxHonorForGrade) { this.maxHonorForGrade = maxHonorForGrade; };
	public int getHonor() { return this.honor; };
	public void setHonor(int honor) { this.honor = honor; };
	public int getHonorDelta() { return this.honorDelta; };
	public void setHonorDelta(int honorDelta) { this.honorDelta = honorDelta; };

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

}
