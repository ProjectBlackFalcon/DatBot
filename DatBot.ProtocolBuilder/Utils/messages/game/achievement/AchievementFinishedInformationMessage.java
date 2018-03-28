package protocol.network.messages.game.achievement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.achievement.AchievementFinishedMessage;

@SuppressWarnings("unused")
public class AchievementFinishedInformationMessage extends AchievementFinishedMessage {
	public static final int ProtocolId = 6381;

	private String name;
	private long playerId;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };
	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };

	public AchievementFinishedInformationMessage(){
	}

	public AchievementFinishedInformationMessage(String name, long playerId){
		this.name = name;
		this.playerId = playerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.name);
			writer.writeVarLong(this.playerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.name = reader.readUTF();
			this.playerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
