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
public class TreasureHuntFlagRequestAnswerMessage extends NetworkMessage {
	public static final int ProtocolId = 6507;

	public int questType;
	public int result;
	public int index;

	public TreasureHuntFlagRequestAnswerMessage(){
	}

	public TreasureHuntFlagRequestAnswerMessage(int questType, int result, int index){
		this.questType = questType;
		this.result = result;
		this.index = index;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.questType);
			writer.writeByte(this.result);
			writer.writeByte(this.index);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questType = reader.readByte();
			this.result = reader.readByte();
			this.index = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("questType : " + this.questType);
		//Network.appendDebug("result : " + this.result);
		//Network.appendDebug("index : " + this.index);
	//}
}
