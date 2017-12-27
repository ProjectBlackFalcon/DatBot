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
import protocol.network.NetworkMessage;
import protocol.network.types.game.context.roleplay.fight.arena.ArenaRankInfos;

@SuppressWarnings("unused")
public class GameRolePlayArenaUpdatePlayerInfosMessage extends NetworkMessage {
	public static final int ProtocolId = 6301;

	private ArenaRankInfos solo;

	public ArenaRankInfos getSolo() { return this.solo; };
	public void setSolo(ArenaRankInfos solo) { this.solo = solo; };

	public GameRolePlayArenaUpdatePlayerInfosMessage(){
	}

	public GameRolePlayArenaUpdatePlayerInfosMessage(ArenaRankInfos solo){
		this.solo = solo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			solo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.solo = new ArenaRankInfos();
			this.solo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
