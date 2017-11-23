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
import protocol.network.types.game.context.fight.GameFightFighterLightInformations;

@SuppressWarnings("unused")
public class GameFightFighterMonsterLightInformations extends GameFightFighterLightInformations {
	public static final int ProtocolId = 455;

	public int creatureGenericId;

	public GameFightFighterMonsterLightInformations(){
	}

	public GameFightFighterMonsterLightInformations(int creatureGenericId){
		this.creatureGenericId = creatureGenericId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.creatureGenericId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.creatureGenericId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("creatureGenericId : " + this.creatureGenericId);
	//}
}
