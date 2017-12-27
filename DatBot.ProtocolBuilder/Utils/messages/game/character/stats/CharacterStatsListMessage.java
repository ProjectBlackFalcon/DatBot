package protocol.network.messages.game.character.stats;

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
import protocol.network.types.game.character.characteristic.CharacterCharacteristicsInformations;

@SuppressWarnings("unused")
public class CharacterStatsListMessage extends NetworkMessage {
	public static final int ProtocolId = 500;

	private CharacterCharacteristicsInformations stats;

	public CharacterCharacteristicsInformations getStats() { return this.stats; };
	public void setStats(CharacterCharacteristicsInformations stats) { this.stats = stats; };

	public CharacterStatsListMessage(){
	}

	public CharacterStatsListMessage(CharacterCharacteristicsInformations stats){
		this.stats = stats;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			stats.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.stats = new CharacterCharacteristicsInformations();
			this.stats.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
