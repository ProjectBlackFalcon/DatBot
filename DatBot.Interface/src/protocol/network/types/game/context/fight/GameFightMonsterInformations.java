package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.GameFightAIInformations;

@SuppressWarnings("unused")
public class GameFightMonsterInformations extends GameFightAIInformations {
	public static final int ProtocolId = 29;

	public int creatureGenericId;
	public int creatureGrade;

	public GameFightMonsterInformations(){
	}

	public GameFightMonsterInformations(int creatureGenericId, int creatureGrade){
		this.creatureGenericId = creatureGenericId;
		this.creatureGrade = creatureGrade;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.creatureGenericId);
			writer.writeByte(this.creatureGrade);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.creatureGenericId = reader.readVarShort();
			this.creatureGrade = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("creatureGenericId : " + this.creatureGenericId);
		//Network.appendDebug("creatureGrade : " + this.creatureGrade);
	//}
}
