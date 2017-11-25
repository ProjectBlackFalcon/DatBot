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
public class GameFightTaxCollectorInformations extends GameFightAIInformations {
	public static final int ProtocolId = 48;

	public int firstNameId;
	public int lastNameId;
	public int level;

	public GameFightTaxCollectorInformations(){
	}

	public GameFightTaxCollectorInformations(int firstNameId, int lastNameId, int level){
		this.firstNameId = firstNameId;
		this.lastNameId = lastNameId;
		this.level = level;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.firstNameId);
			writer.writeVarShort(this.lastNameId);
			writer.writeByte(this.level);
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
			this.level = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("firstNameId : " + this.firstNameId);
		//Network.appendDebug("lastNameId : " + this.lastNameId);
		//Network.appendDebug("level : " + this.level);
	//}
}