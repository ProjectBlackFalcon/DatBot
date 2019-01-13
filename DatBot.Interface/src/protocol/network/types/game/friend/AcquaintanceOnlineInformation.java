package protocol.network.types.game.friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.friend.AcquaintanceInformation;
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class AcquaintanceOnlineInformation extends AcquaintanceInformation {
	public static final int ProtocolId = 562;

	private long playerId;
	private String playerName;
	private int moodSmileyId;
	private PlayerStatus status;

	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public String getPlayerName() { return this.playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; };
	public int getMoodSmileyId() { return this.moodSmileyId; }
	public void setMoodSmileyId(int moodSmileyId) { this.moodSmileyId = moodSmileyId; };
	public PlayerStatus getStatus() { return this.status; }
	public void setStatus(PlayerStatus status) { this.status = status; };

	public AcquaintanceOnlineInformation(){
	}

	public AcquaintanceOnlineInformation(long playerId, String playerName, int moodSmileyId, PlayerStatus status){
		this.playerId = playerId;
		this.playerName = playerName;
		this.moodSmileyId = moodSmileyId;
		this.status = status;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
			writer.writeVarShort(this.moodSmileyId);
			writer.writeShort(PlayerStatus.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerId = reader.readVarLong();
			this.playerName = reader.readUTF();
			this.moodSmileyId = reader.readVarShort();
			this.status = (PlayerStatus) ProtocolTypeManager.getInstance(reader.readShort());
			this.status.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
