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
public class GameFightJoinRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 701;

	private double fighterId;
	private int fightId;

	public double getFighterId() { return this.fighterId; };
	public void setFighterId(double fighterId) { this.fighterId = fighterId; };
	public int getFightId() { return this.fightId; };
	public void setFightId(int fightId) { this.fightId = fightId; };

	public GameFightJoinRequestMessage(){
	}

	public GameFightJoinRequestMessage(double fighterId, int fightId){
		this.fighterId = fighterId;
		this.fightId = fightId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.fighterId);
			writer.writeVarShort(this.fightId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fighterId = reader.readDouble();
			this.fightId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
