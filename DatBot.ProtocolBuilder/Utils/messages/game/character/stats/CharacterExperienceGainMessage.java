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

	private long experienceCharacter;
	private long experienceMount;
	private long experienceGuild;
	private long experienceIncarnation;

	public long getExperienceCharacter() { return this.experienceCharacter; };
	public void setExperienceCharacter(long experienceCharacter) { this.experienceCharacter = experienceCharacter; };
	public long getExperienceMount() { return this.experienceMount; };
	public void setExperienceMount(long experienceMount) { this.experienceMount = experienceMount; };
	public long getExperienceGuild() { return this.experienceGuild; };
	public void setExperienceGuild(long experienceGuild) { this.experienceGuild = experienceGuild; };
	public long getExperienceIncarnation() { return this.experienceIncarnation; };
	public void setExperienceIncarnation(long experienceIncarnation) { this.experienceIncarnation = experienceIncarnation; };

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
	}

}
