package protocol.network.messages.game.context.fight;

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
import protocol.network.types.game.context.fight.FightTeamInformations;

@SuppressWarnings("unused")
public class GameFightUpdateTeamMessage extends NetworkMessage {
	public static final int ProtocolId = 5572;

	private int fightId;
	private FightTeamInformations team;

	public int getFightId() { return this.fightId; };
	public void setFightId(int fightId) { this.fightId = fightId; };
	public FightTeamInformations getTeam() { return this.team; };
	public void setTeam(FightTeamInformations team) { this.team = team; };

	public GameFightUpdateTeamMessage(){
	}

	public GameFightUpdateTeamMessage(int fightId, FightTeamInformations team){
		this.fightId = fightId;
		this.team = team;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			team.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.team = new FightTeamInformations();
			this.team.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
