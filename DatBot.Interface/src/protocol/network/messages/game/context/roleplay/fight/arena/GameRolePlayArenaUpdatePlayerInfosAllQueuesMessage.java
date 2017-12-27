package protocol.network.messages.game.context.roleplay.fight.arena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.fight.arena.GameRolePlayArenaUpdatePlayerInfosMessage;
import protocol.network.types.game.context.roleplay.fight.arena.ArenaRankInfos;

@SuppressWarnings("unused")
public class GameRolePlayArenaUpdatePlayerInfosAllQueuesMessage extends GameRolePlayArenaUpdatePlayerInfosMessage {
	public static final int ProtocolId = 6728;

	private ArenaRankInfos team;
	private ArenaRankInfos duel;

	public ArenaRankInfos getTeam() { return this.team; };
	public void setTeam(ArenaRankInfos team) { this.team = team; };
	public ArenaRankInfos getDuel() { return this.duel; };
	public void setDuel(ArenaRankInfos duel) { this.duel = duel; };

	public GameRolePlayArenaUpdatePlayerInfosAllQueuesMessage(){
	}

	public GameRolePlayArenaUpdatePlayerInfosAllQueuesMessage(ArenaRankInfos team, ArenaRankInfos duel){
		this.team = team;
		this.duel = duel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			team.Serialize(writer);
			duel.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.team = new ArenaRankInfos();
			this.team.Deserialize(reader);
			this.duel = new ArenaRankInfos();
			this.duel.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
