package protocol.network.types.game.character.choice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.CharacterMinimalPlusLookInformations;

@SuppressWarnings("unused")
public class CharacterBaseInformations extends CharacterMinimalPlusLookInformations {
	public static final int ProtocolId = 45;

	public int breed;
	public boolean sex;

	public CharacterBaseInformations(){
	}

	public CharacterBaseInformations(int breed, boolean sex){
		this.breed = breed;
		this.sex = sex;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
	//}
}
