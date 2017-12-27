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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class GameFightNewWaveMessage extends NetworkMessage {
	public static final int ProtocolId = 6490;

	private int id;
	private int teamId;
	private int nbTurnBeforeNextWave;

	public int getId() { return this.id; };
	public void setId(int id) { this.id = id; };
	public int getTeamId() { return this.teamId; };
	public void setTeamId(int teamId) { this.teamId = teamId; };
	public int getNbTurnBeforeNextWave() { return this.nbTurnBeforeNextWave; };
	public void setNbTurnBeforeNextWave(int nbTurnBeforeNextWave) { this.nbTurnBeforeNextWave = nbTurnBeforeNextWave; };

	public GameFightNewWaveMessage(){
	}

	public GameFightNewWaveMessage(int id, int teamId, int nbTurnBeforeNextWave){
		this.id = id;
		this.teamId = teamId;
		this.nbTurnBeforeNextWave = nbTurnBeforeNextWave;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.id);
			writer.writeByte(this.teamId);
			writer.writeShort(this.nbTurnBeforeNextWave);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readByte();
			this.teamId = reader.readByte();
			this.nbTurnBeforeNextWave = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
