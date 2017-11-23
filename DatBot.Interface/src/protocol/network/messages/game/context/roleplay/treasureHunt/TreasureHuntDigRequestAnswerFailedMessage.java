package protocol.network.messages.game.context.roleplay.treasureHunt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.treasureHunt.TreasureHuntDigRequestAnswerMessage;

@SuppressWarnings("unused")
public class TreasureHuntDigRequestAnswerFailedMessage extends TreasureHuntDigRequestAnswerMessage {
	public static final int ProtocolId = 6509;

	public int wrongFlagCount;

	public TreasureHuntDigRequestAnswerFailedMessage(){
	}

	public TreasureHuntDigRequestAnswerFailedMessage(int wrongFlagCount){
		this.wrongFlagCount = wrongFlagCount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.wrongFlagCount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.wrongFlagCount = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("wrongFlagCount : " + this.wrongFlagCount);
	//}
}
