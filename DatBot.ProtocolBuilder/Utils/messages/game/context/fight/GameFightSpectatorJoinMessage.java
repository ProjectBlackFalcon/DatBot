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
import protocol.network.messages.game.context.fight.GameFightJoinMessage;
import protocol.network.types.game.context.roleplay.party.NamedPartyTeam;

@SuppressWarnings("unused")
public class GameFightSpectatorJoinMessage extends GameFightJoinMessage {
	public static final int ProtocolId = 6504;

	private List<NamedPartyTeam> namedPartyTeams;

	public List<NamedPartyTeam> getNamedPartyTeams() { return this.namedPartyTeams; };
	public void setNamedPartyTeams(List<NamedPartyTeam> namedPartyTeams) { this.namedPartyTeams = namedPartyTeams; };

	public GameFightSpectatorJoinMessage(){
	}

	public GameFightSpectatorJoinMessage(List<NamedPartyTeam> namedPartyTeams){
		this.namedPartyTeams = namedPartyTeams;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.namedPartyTeams.size());
			int _loc2_ = 0;
			while( _loc2_ < this.namedPartyTeams.size()){
				this.namedPartyTeams.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.namedPartyTeams = new ArrayList<NamedPartyTeam>();
			while( _loc3_ <  _loc2_){
				NamedPartyTeam _loc15_ = new NamedPartyTeam();
				_loc15_.Deserialize(reader);
				this.namedPartyTeams.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
