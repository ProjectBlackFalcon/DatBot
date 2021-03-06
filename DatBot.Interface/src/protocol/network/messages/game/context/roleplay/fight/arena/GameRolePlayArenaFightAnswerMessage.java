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
public class GameRolePlayArenaFightAnswerMessage extends NetworkMessage {
	public static final int ProtocolId = 6279;

	private int fightId;
	private boolean accept;

	public int getFightId() { return this.fightId; }
	public void setFightId(int fightId) { this.fightId = fightId; };
	public boolean isAccept() { return this.accept; }
	public void setAccept(boolean accept) { this.accept = accept; };

	public GameRolePlayArenaFightAnswerMessage(){
	}

	public GameRolePlayArenaFightAnswerMessage(int fightId, boolean accept){
		this.fightId = fightId;
		this.accept = accept;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			writer.writeBoolean(this.accept);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.accept = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
