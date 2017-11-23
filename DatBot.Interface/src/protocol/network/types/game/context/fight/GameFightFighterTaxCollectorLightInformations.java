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
public class GameFightFighterTaxCollectorLightInformations extends GameFightFighterLightInformations {
	public static final int ProtocolId = 457;

	public int firstNameId;
	public int lastNameId;

	public GameFightFighterTaxCollectorLightInformations(){
	}

	public GameFightFighterTaxCollectorLightInformations(int firstNameId, int lastNameId){
		this.firstNameId = firstNameId;
		this.lastNameId = lastNameId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.firstNameId);
			writer.writeVarShort(this.lastNameId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.firstNameId = reader.readVarShort();
			this.lastNameId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("firstNameId : " + this.firstNameId);
		//Network.appendDebug("lastNameId : " + this.lastNameId);
	//}
}
