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
public class GameRolePlayAttackMonsterRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6191;

	public double monsterGroupId;

	public GameRolePlayAttackMonsterRequestMessage(){
	}

	public GameRolePlayAttackMonsterRequestMessage(double monsterGroupId){
		this.monsterGroupId = monsterGroupId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.monsterGroupId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.monsterGroupId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("monsterGroupId : " + this.monsterGroupId);
	//}
}
