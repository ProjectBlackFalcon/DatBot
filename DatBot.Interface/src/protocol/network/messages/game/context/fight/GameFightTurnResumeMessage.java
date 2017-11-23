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
import protocol.network.messages.game.context.fight.GameFightTurnStartMessage;

@SuppressWarnings("unused")
public class GameFightTurnResumeMessage extends GameFightTurnStartMessage {
	public static final int ProtocolId = 6307;

	public int remainingTime;

	public GameFightTurnResumeMessage(){
	}

	public GameFightTurnResumeMessage(int remainingTime){
		this.remainingTime = remainingTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.remainingTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.remainingTime = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("remainingTime : " + this.remainingTime);
	//}
}
