package protocol.network.messages.game.actions.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.actions.AbstractGameActionMessage;

@SuppressWarnings("unused")
public class GameActionFightReduceDamagesMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5526;

	public double targetId;
	public int amount;

	public GameActionFightReduceDamagesMessage(){
	}

	public GameActionFightReduceDamagesMessage(double targetId, int amount){
		this.targetId = targetId;
		this.amount = amount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeVarInt(this.amount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.amount = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("targetId : " + this.targetId);
		//Network.appendDebug("amount : " + this.amount);
	//}
}