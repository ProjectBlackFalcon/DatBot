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

	public int id;
	public int teamId;
	public int nbTurnBeforeNextWave;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("teamId : " + this.teamId);
		//Network.appendDebug("nbTurnBeforeNextWave : " + this.nbTurnBeforeNextWave);
	//}
}
