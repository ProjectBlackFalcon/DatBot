package protocol.network.types.game.context.roleplay.party;

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
import protocol.network.types.game.context.roleplay.party.NamedPartyTeam;

@SuppressWarnings("unused")
public class NamedPartyTeamWithOutcome extends NetworkMessage {
	public static final int ProtocolId = 470;

	private NamedPartyTeam team;
	private int outcome;

	public NamedPartyTeam getTeam() { return this.team; }
	public void setTeam(NamedPartyTeam team) { this.team = team; };
	public int getOutcome() { return this.outcome; }
	public void setOutcome(int outcome) { this.outcome = outcome; };

	public NamedPartyTeamWithOutcome(){
	}

	public NamedPartyTeamWithOutcome(NamedPartyTeam team, int outcome){
		this.team = team;
		this.outcome = outcome;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			team.Serialize(writer);
			writer.writeVarShort(this.outcome);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.team = new NamedPartyTeam();
			this.team.Deserialize(reader);
			this.outcome = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
