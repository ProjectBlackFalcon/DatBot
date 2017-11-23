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

	public int questLevel;
	public int questType;

	public TreasureHuntRequestMessage(){
	}

	public TreasureHuntRequestMessage(int questLevel, int questType){
		this.questLevel = questLevel;
		this.questType = questType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.questLevel);
			writer.writeByte(this.questType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.questLevel = reader.readByte();
			this.questType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("questLevel : " + this.questLevel);
		//Network.appendDebug("questType : " + this.questType);
	//}
}
