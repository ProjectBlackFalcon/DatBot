package protocol.network.types.game.character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.CharacterMinimalInformations;
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class CharacterMinimalPlusLookInformations extends CharacterMinimalInformations {
	public static final int ProtocolId = 163;

	public EntityLook entityLook;

	public CharacterMinimalPlusLookInformations(){
	}

	public CharacterMinimalPlusLookInformations(EntityLook entityLook){
		this.entityLook = entityLook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			entityLook.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.entityLook = new EntityLook();
			this.entityLook.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("entityLook : " + this.entityLook);
	//}
}