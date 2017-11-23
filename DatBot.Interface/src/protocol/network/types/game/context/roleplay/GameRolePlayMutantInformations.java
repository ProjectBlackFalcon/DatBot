package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.GameRolePlayHumanoidInformations;

@SuppressWarnings("unused")
public class GameRolePlayMutantInformations extends GameRolePlayHumanoidInformations {
	public static final int ProtocolId = 3;

	public int monsterId;
	public int powerLevel;

	public GameRolePlayMutantInformations(){
	}

	public GameRolePlayMutantInformations(int monsterId, int powerLevel){
		this.monsterId = monsterId;
		this.powerLevel = powerLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.monsterId);
			writer.writeByte(this.powerLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.monsterId = reader.readVarShort();
			this.powerLevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("monsterId : " + this.monsterId);
		//Network.appendDebug("powerLevel : " + this.powerLevel);
	//}
}
