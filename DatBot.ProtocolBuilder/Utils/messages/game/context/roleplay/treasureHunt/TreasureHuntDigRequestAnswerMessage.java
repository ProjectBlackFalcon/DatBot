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
public class TreasureHuntDigRequestAnswerMessage extends NetworkMessage {
	public static final int ProtocolId = 6484;

	private int questType;
	private int result;

	public int getQuestType() { return this.questType; };
	public void setQuestType(int questType) { this.questType = questType; };
	public int getResult() { return this.result; };
	public void setResult(int result) { this.result = result; };

	public TreasureHuntDigRequestAnswerMessage(){
	}

	public TreasureHuntDigRequestAnswerMessage(int questType, int result){
		this.questType = questType;
		this.result = result;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.questType);
			writer.writeByte(this.result);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questType = reader.readByte();
			this.result = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
