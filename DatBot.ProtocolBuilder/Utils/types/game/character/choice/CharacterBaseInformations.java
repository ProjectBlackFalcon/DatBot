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

	private boolean sex;

	public boolean isSex() { return this.sex; };
	public void setSex(boolean sex) { this.sex = sex; };

	public CharacterBaseInformations(){
	}

	public CharacterBaseInformations(boolean sex){
		this.sex = sex;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.sex);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.sex = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
