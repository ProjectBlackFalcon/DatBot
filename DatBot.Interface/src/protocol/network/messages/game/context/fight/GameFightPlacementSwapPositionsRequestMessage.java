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
import protocol.network.messages.game.context.fight.GameFightPlacementPositionRequestMessage;

@SuppressWarnings("unused")
public class GameFightPlacementSwapPositionsRequestMessage extends GameFightPlacementPositionRequestMessage {
	public static final int ProtocolId = 6541;

	private double requestedId;

	public double getRequestedId() { return this.requestedId; }
	public void setRequestedId(double requestedId) { this.requestedId = requestedId; };

	public GameFightPlacementSwapPositionsRequestMessage(){
	}

	public GameFightPlacementSwapPositionsRequestMessage(double requestedId){
		this.requestedId = requestedId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.requestedId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.requestedId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
