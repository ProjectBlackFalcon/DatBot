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

@SuppressWarnings("unused")
public class CharacterMinimalGuildPublicInformations extends CharacterMinimalInformations {
	public static final int ProtocolId = 556;

	private int rank;

	public int getRank() { return this.rank; }
	public void setRank(int rank) { this.rank = rank; };

	public CharacterMinimalGuildPublicInformations(){
	}

	public CharacterMinimalGuildPublicInformations(int rank){
		this.rank = rank;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.rank);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.rank = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
