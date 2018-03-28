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

	private int questType;
	private int result;
	private int index;

	public int getQuestType() { return this.questType; }
	public void setQuestType(int questType) { this.questType = questType; };
	public int getResult() { return this.result; }
	public void setResult(int result) { this.result = result; };
	public int getIndex() { return this.index; }
	public void setIndex(int index) { this.index = index; };

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
	}

}
