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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class GameFightStartingMessage extends NetworkMessage {
	public static final int ProtocolId = 700;

	public int fightType;
	public int fightId;
	public double attackerId;
	public double defenderId;

	public GameFightStartingMessage(){
	}

	public GameFightStartingMessage(int fightType, int fightId, double attackerId, double defenderId){
		this.fightType = fightType;
		this.fightId = fightId;
		this.attackerId = attackerId;
		this.defenderId = defenderId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.fightType);
			writer.writeVarShort(this.fightId);
			writer.writeDouble(this.attackerId);
			writer.writeDouble(this.defenderId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightType = reader.readByte();
			this.fightId = reader.readVarShort();
			this.attackerId = reader.readDouble();
			this.defenderId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightType : " + this.fightType);
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("attackerId : " + this.attackerId);
		//Network.appendDebug("defenderId : " + this.defenderId);
	//}
}
