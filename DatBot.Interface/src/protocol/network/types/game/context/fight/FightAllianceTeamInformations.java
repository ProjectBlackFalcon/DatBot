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
import protocol.network.types.game.context.fight.FightTeamInformations;

@SuppressWarnings("unused")
public class FightAllianceTeamInformations extends FightTeamInformations {
	public static final int ProtocolId = 439;

	private int relation;

	public int getRelation() { return this.relation; }
	public void setRelation(int relation) { this.relation = relation; };

	public FightAllianceTeamInformations(){
	}

	public FightAllianceTeamInformations(int relation){
		this.relation = relation;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.relation);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.relation = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
