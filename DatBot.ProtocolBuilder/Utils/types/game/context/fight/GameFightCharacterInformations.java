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
import protocol.network.types.game.context.fight.GameFightFighterNamedInformations;
import protocol.network.types.game.character.alignment.ActorAlignmentInformations;

@SuppressWarnings("unused")
public class GameFightCharacterInformations extends GameFightFighterNamedInformations {
	public static final int ProtocolId = 46;

	private int level;
	private ActorAlignmentInformations alignmentInfos;
	private int breed;
	private boolean sex;

	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };
	public ActorAlignmentInformations getAlignmentInfos() { return this.alignmentInfos; }
	public void setAlignmentInfos(ActorAlignmentInformations alignmentInfos) { this.alignmentInfos = alignmentInfos; };
	public int getBreed() { return this.breed; }
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; }
	public void setSex(boolean sex) { this.sex = sex; };

	public GameFightCharacterInformations(){
	}

	public GameFightCharacterInformations(int level, ActorAlignmentInformations alignmentInfos, int breed, boolean sex){
		this.level = level;
		this.alignmentInfos = alignmentInfos;
		this.breed = breed;
		this.sex = sex;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.level);
			alignmentInfos.Serialize(writer);
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
			this.level = reader.readVarShort();
			this.alignmentInfos = new ActorAlignmentInformations();
			this.alignmentInfos.Deserialize(reader);
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
