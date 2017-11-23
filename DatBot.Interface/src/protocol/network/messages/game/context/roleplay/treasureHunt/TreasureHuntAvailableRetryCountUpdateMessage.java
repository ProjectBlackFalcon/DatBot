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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class TreasureHuntAvailableRetryCountUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6491;

	public int questType;
	public int availableRetryCount;

	public TreasureHuntAvailableRetryCountUpdateMessage(){
	}

	public TreasureHuntAvailableRetryCountUpdateMessage(int questType, int availableRetryCount){
		this.questType = questType;
		this.availableRetryCount = availableRetryCount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.questType);
			writer.writeInt(this.availableRetryCount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questType = reader.readByte();
			this.availableRetryCount = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("questType : " + this.questType);
		//Network.appendDebug("availableRetryCount : " + this.availableRetryCount);
	//}
}
