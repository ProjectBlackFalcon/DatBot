package protocol.network.types.game.finishmoves;

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
public class FinishMoveInformations extends NetworkMessage {
	public static final int ProtocolId = 506;

	public int finishMoveId;
	public boolean finishMoveState;

	public FinishMoveInformations(){
	}

	public FinishMoveInformations(int finishMoveId, boolean finishMoveState){
		this.finishMoveId = finishMoveId;
		this.finishMoveState = finishMoveState;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.finishMoveId);
			writer.writeBoolean(this.finishMoveState);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.finishMoveId = reader.readInt();
			this.finishMoveState = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("finishMoveId : " + this.finishMoveId);
		//Network.appendDebug("finishMoveState : " + this.finishMoveState);
	//}
}