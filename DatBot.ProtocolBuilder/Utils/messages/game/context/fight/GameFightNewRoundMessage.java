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
public class GameFightNewRoundMessage extends NetworkMessage {
	public static final int ProtocolId = 6239;

	private int roundNumber;

	public int getRoundNumber() { return this.roundNumber; }
	public void setRoundNumber(int roundNumber) { this.roundNumber = roundNumber; };

	public GameFightNewRoundMessage(){
	}

	public GameFightNewRoundMessage(int roundNumber){
		this.roundNumber = roundNumber;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.roundNumber);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.roundNumber = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
