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
public class GameFightTurnStartMessage extends NetworkMessage {
	public static final int ProtocolId = 714;

	private double id;
	private int waitTime;

	public double getId() { return this.id; }
	public void setId(double id) { this.id = id; };
	public int getWaitTime() { return this.waitTime; }
	public void setWaitTime(int waitTime) { this.waitTime = waitTime; };

	public GameFightTurnStartMessage(){
	}

	public GameFightTurnStartMessage(double id, int waitTime){
		this.id = id;
		this.waitTime = waitTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.id);
			writer.writeVarInt(this.waitTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readDouble();
			this.waitTime = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
