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
public class GameFightPlacementSwapPositionsCancelMessage extends NetworkMessage {
	public static final int ProtocolId = 6543;

	private int requestId;

	public int getRequestId() { return this.requestId; };
	public void setRequestId(int requestId) { this.requestId = requestId; };

	public GameFightPlacementSwapPositionsCancelMessage(){
	}

	public GameFightPlacementSwapPositionsCancelMessage(int requestId){
		this.requestId = requestId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.requestId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.requestId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
