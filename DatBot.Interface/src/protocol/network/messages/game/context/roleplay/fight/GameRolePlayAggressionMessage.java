package protocol.network.messages.game.context.roleplay.fight;

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
public class GameRolePlayAggressionMessage extends NetworkMessage {
	public static final int ProtocolId = 6073;

	public long attackerId;
	public long defenderId;

	public GameRolePlayAggressionMessage(){
	}

	public GameRolePlayAggressionMessage(long attackerId, long defenderId){
		this.attackerId = attackerId;
		this.defenderId = defenderId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.attackerId);
			writer.writeVarLong(this.defenderId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.attackerId = reader.readVarLong();
			this.defenderId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("attackerId : " + this.attackerId);
		//Network.appendDebug("defenderId : " + this.defenderId);
	//}
}
