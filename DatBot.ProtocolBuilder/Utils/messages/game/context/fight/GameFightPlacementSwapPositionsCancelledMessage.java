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
public class GameFightPlacementSwapPositionsCancelledMessage extends NetworkMessage {
	public static final int ProtocolId = 6546;

	private int requestId;
	private double cancellerId;

	public int getRequestId() { return this.requestId; };
	public void setRequestId(int requestId) { this.requestId = requestId; };
	public double getCancellerId() { return this.cancellerId; };
	public void setCancellerId(double cancellerId) { this.cancellerId = cancellerId; };

	public GameFightPlacementSwapPositionsCancelledMessage(){
	}

	public GameFightPlacementSwapPositionsCancelledMessage(int requestId, double cancellerId){
		this.requestId = requestId;
		this.cancellerId = cancellerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.requestId);
			writer.writeDouble(this.cancellerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.requestId = reader.readInt();
			this.cancellerId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
