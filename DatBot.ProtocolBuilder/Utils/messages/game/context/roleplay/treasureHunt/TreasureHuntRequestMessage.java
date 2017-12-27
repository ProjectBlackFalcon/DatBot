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
public class TreasureHuntRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6488;

	private int questLevel;
	private int questType;

	public int getQuestLevel() { return this.questLevel; };
	public void setQuestLevel(int questLevel) { this.questLevel = questLevel; };
	public int getQuestType() { return this.questType; };
	public void setQuestType(int questType) { this.questType = questType; };

	public TreasureHuntRequestMessage(){
	}

	public TreasureHuntRequestMessage(int questLevel, int questType){
		this.questLevel = questLevel;
		this.questType = questType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.questLevel);
			writer.writeByte(this.questType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questLevel = reader.readVarShort();
			this.questType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
