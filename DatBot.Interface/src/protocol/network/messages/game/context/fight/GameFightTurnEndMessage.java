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
public class GameFightTurnEndMessage extends NetworkMessage {
	public static final int ProtocolId = 719;

	private double id;

	public double getId() { return this.id; }
	public void setId(double id) { this.id = id; };

	public GameFightTurnEndMessage(){
	}

	public GameFightTurnEndMessage(double id){
		this.id = id;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
