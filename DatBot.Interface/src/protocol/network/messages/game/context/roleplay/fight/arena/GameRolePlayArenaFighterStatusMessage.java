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

@SuppressWarnings("unused")
public class GameRolePlayArenaFighterStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6281;

	public int fightId;
	public int playerId;
	public boolean accepted;

	public GameRolePlayArenaFighterStatusMessage(){
	}

	public GameRolePlayArenaFighterStatusMessage(int fightId, int playerId, boolean accepted){
		this.fightId = fightId;
		this.playerId = playerId;
		this.accepted = accepted;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			writer.writeInt(this.playerId);
			writer.writeBoolean(this.accepted);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.playerId = reader.readInt();
			this.accepted = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("accepted : " + this.accepted);
	//}
}
