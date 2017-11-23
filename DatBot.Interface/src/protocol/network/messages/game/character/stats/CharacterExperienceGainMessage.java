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

@SuppressWarnings("unused")
public class CharacterExperienceGainMessage extends NetworkMessage {
	public static final int ProtocolId = 6321;

	public long experienceCharacter;
	public long experienceMount;
	public long experienceGuild;
	public long experienceIncarnation;

	public CharacterExperienceGainMessage(){
	}

	public CharacterExperienceGainMessage(long experienceCharacter, long experienceMount, long experienceGuild, long experienceIncarnation){
		this.experienceCharacter = experienceCharacter;
		this.experienceMount = experienceMount;
		this.experienceGuild = experienceGuild;
		this.experienceIncarnation = experienceIncarnation;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.experienceCharacter);
			writer.writeVarLong(this.experienceMount);
			writer.writeVarLong(this.experienceGuild);
			writer.writeVarLong(this.experienceIncarnation);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.experienceCharacter = reader.readVarLong();
			this.experienceMount = reader.readVarLong();
			this.experienceGuild = reader.readVarLong();
			this.experienceIncarnation = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("experienceCharacter : " + this.experienceCharacter);
		//Network.appendDebug("experienceMount : " + this.experienceMount);
		//Network.appendDebug("experienceGuild : " + this.experienceGuild);
		//Network.appendDebug("experienceIncarnation : " + this.experienceIncarnation);
	//}
}
