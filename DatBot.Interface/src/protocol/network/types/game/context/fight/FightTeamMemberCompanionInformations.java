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
public class FightTeamMemberCompanionInformations extends FightTeamMemberInformations {
	public static final int ProtocolId = 451;

	private int companionId;
	private int level;
	private double masterId;

	public int getCompanionId() { return this.companionId; }
	public void setCompanionId(int companionId) { this.companionId = companionId; };
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };
	public double getMasterId() { return this.masterId; }
	public void setMasterId(double masterId) { this.masterId = masterId; };

	public FightTeamMemberCompanionInformations(){
	}

	public FightTeamMemberCompanionInformations(int companionId, int level, double masterId){
		this.companionId = companionId;
		this.level = level;
		this.masterId = masterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.companionId);
			writer.writeVarShort(this.level);
			writer.writeDouble(this.masterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.companionId = reader.readByte();
			this.level = reader.readVarShort();
			this.masterId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
