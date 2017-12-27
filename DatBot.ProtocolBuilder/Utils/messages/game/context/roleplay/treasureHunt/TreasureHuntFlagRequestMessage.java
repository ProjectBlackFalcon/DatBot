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
public class TreasureHuntFlagRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6508;

	private int questType;
	private int index;

	public int getQuestType() { return this.questType; };
	public void setQuestType(int questType) { this.questType = questType; };
	public int getIndex() { return this.index; };
	public void setIndex(int index) { this.index = index; };

	public TreasureHuntFlagRequestMessage(){
	}

	public TreasureHuntFlagRequestMessage(int questType, int index){
		this.questType = questType;
		this.index = index;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.questType);
			writer.writeByte(this.index);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questType = reader.readByte();
			this.index = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
