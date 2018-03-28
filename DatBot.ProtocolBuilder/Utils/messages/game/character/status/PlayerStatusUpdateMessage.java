package protocol.network.messages.game.character.status;

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
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class PlayerStatusUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6386;

	private int accountId;
	private long playerId;
	private PlayerStatus status;

	public int getAccountId() { return this.accountId; }
	public void setAccountId(int accountId) { this.accountId = accountId; };
	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public PlayerStatus getStatus() { return this.status; }
	public void setStatus(PlayerStatus status) { this.status = status; };

	public PlayerStatusUpdateMessage(){
	}

	public PlayerStatusUpdateMessage(int accountId, long playerId, PlayerStatus status){
		this.accountId = accountId;
		this.playerId = playerId;
		this.status = status;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.accountId);
			writer.writeVarLong(this.playerId);
			writer.writeShort(PlayerStatus.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.accountId = reader.readInt();
			this.playerId = reader.readVarLong();
			this.status = (PlayerStatus) ProtocolTypeManager.getInstance(reader.readShort());
			this.status.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
