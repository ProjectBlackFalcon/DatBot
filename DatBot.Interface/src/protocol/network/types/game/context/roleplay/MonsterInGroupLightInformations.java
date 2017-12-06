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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class MonsterInGroupLightInformations extends NetworkMessage {
	public static final int ProtocolId = 395;

	public int creatureGenericId;
	public int grade;

	public MonsterInGroupLightInformations(){
	}

	public MonsterInGroupLightInformations(int creatureGenericId, int grade){
		this.creatureGenericId = creatureGenericId;
		this.grade = grade;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.creatureGenericId);
			writer.writeByte(this.grade);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.creatureGenericId = reader.readInt();
			this.grade = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("creatureGenericId : " + this.creatureGenericId);
		//Network.appendDebug("grade : " + this.grade);
	//}
}
