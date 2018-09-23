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

	private int fightId;
	private double playerId;
	private boolean accepted;

	public int getFightId() { return this.fightId; }
	public void setFightId(int fightId) { this.fightId = fightId; };
	public double getPlayerId() { return this.playerId; }
	public void setPlayerId(double playerId) { this.playerId = playerId; };
	public boolean isAccepted() { return this.accepted; }
	public void setAccepted(boolean accepted) { this.accepted = accepted; };

	public GameRolePlayArenaFighterStatusMessage(){
	}

	public GameRolePlayArenaFighterStatusMessage(int fightId, double playerId, boolean accepted){
		this.fightId = fightId;
		this.playerId = playerId;
		this.accepted = accepted;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			writer.writeDouble(this.playerId);
			writer.writeBoolean(this.accepted);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.playerId = reader.readDouble();
			this.accepted = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
