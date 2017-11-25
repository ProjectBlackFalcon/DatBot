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

	public int requestId;
	public double requesterId;
	public int requesterCellId;
	public double requestedId;
	public int requestedCellId;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("requestId : " + this.requestId);
		//Network.appendDebug("requesterId : " + this.requesterId);
		//Network.appendDebug("requesterCellId : " + this.requesterCellId);
		//Network.appendDebug("requestedId : " + this.requestedId);
		//Network.appendDebug("requestedCellId : " + this.requestedCellId);
	//}
}