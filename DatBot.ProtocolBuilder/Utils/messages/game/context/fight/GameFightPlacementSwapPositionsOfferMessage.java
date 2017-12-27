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
public class GameFightPlacementSwapPositionsOfferMessage extends NetworkMessage {
	public static final int ProtocolId = 6542;

	private int requestId;
	private double requesterId;
	private int requesterCellId;
	private double requestedId;
	private int requestedCellId;

	public int getRequestId() { return this.requestId; };
	public void setRequestId(int requestId) { this.requestId = requestId; };
	public double getRequesterId() { return this.requesterId; };
	public void setRequesterId(double requesterId) { this.requesterId = requesterId; };
	public int getRequesterCellId() { return this.requesterCellId; };
	public void setRequesterCellId(int requesterCellId) { this.requesterCellId = requesterCellId; };
	public double getRequestedId() { return this.requestedId; };
	public void setRequestedId(double requestedId) { this.requestedId = requestedId; };
	public int getRequestedCellId() { return this.requestedCellId; };
	public void setRequestedCellId(int requestedCellId) { this.requestedCellId = requestedCellId; };

	public GameFightPlacementSwapPositionsOfferMessage(){
	}

	public GameFightPlacementSwapPositionsOfferMessage(int requestId, double requesterId, int requesterCellId, double requestedId, int requestedCellId){
		this.requestId = requestId;
		this.requesterId = requesterId;
		this.requesterCellId = requesterCellId;
		this.requestedId = requestedId;
		this.requestedCellId = requestedCellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.requestId);
			writer.writeDouble(this.requesterId);
			writer.writeVarShort(this.requesterCellId);
			writer.writeDouble(this.requestedId);
			writer.writeVarShort(this.requestedCellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.requestId = reader.readInt();
			this.requesterId = reader.readDouble();
			this.requesterCellId = reader.readVarShort();
			this.requestedId = reader.readDouble();
			this.requestedCellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
