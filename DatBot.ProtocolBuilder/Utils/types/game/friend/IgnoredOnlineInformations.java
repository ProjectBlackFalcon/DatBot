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
import protocol.network.types.game.friend.IgnoredInformations;

@SuppressWarnings("unused")
public class IgnoredOnlineInformations extends IgnoredInformations {
	public static final int ProtocolId = 105;

	private long playerId;
	private String playerName;
	private int breed;
	private boolean sex;

	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public String getPlayerName() { return this.playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; };
	public int getBreed() { return this.breed; }
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; }
	public void setSex(boolean sex) { this.sex = sex; };

	public IgnoredOnlineInformations(){
	}

	public IgnoredOnlineInformations(long playerId, String playerName, int breed, boolean sex){
		this.playerId = playerId;
		this.playerName = playerName;
		this.breed = breed;
		this.sex = sex;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
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
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
