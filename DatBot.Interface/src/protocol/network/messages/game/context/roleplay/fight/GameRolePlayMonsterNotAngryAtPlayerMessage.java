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
public class GameRolePlayMonsterNotAngryAtPlayerMessage extends NetworkMessage {
	public static final int ProtocolId = 6742;

	public long playerId;
	public double monsterGroupId;

	public GameRolePlayMonsterNotAngryAtPlayerMessage(){
	}

	public GameRolePlayMonsterNotAngryAtPlayerMessage(long playerId, double monsterGroupId){
		this.playerId = playerId;
		this.monsterGroupId = monsterGroupId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.playerId);
			writer.writeDouble(this.monsterGroupId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerId = reader.readVarLong();
			this.monsterGroupId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("monsterGroupId : " + this.monsterGroupId);
	//}
}
