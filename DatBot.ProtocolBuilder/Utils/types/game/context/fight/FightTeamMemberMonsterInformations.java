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
import protocol.network.types.game.context.fight.FightTeamMemberInformations;

@SuppressWarnings("unused")
public class FightTeamMemberMonsterInformations extends FightTeamMemberInformations {
	public static final int ProtocolId = 6;

	private int monsterId;
	private int grade;

	public int getMonsterId() { return this.monsterId; };
	public void setMonsterId(int monsterId) { this.monsterId = monsterId; };
	public int getGrade() { return this.grade; };
	public void setGrade(int grade) { this.grade = grade; };

	public FightTeamMemberMonsterInformations(){
	}

	public FightTeamMemberMonsterInformations(int monsterId, int grade){
		this.monsterId = monsterId;
		this.grade = grade;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.monsterId);
			writer.writeByte(this.grade);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.monsterId = reader.readInt();
			this.grade = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
