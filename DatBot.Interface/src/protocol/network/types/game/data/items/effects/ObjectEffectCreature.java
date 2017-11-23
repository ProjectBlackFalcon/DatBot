package protocol.network.types.game.data.items.effects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.effects.ObjectEffect;

@SuppressWarnings("unused")
public class ObjectEffectCreature extends ObjectEffect {
	public static final int ProtocolId = 71;

	public int monsterFamilyId;

	public ObjectEffectCreature(){
	}

	public ObjectEffectCreature(int monsterFamilyId){
		this.monsterFamilyId = monsterFamilyId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.monsterFamilyId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.monsterFamilyId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("monsterFamilyId : " + this.monsterFamilyId);
	//}
}
