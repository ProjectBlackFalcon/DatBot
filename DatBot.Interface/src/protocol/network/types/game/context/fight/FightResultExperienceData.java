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
import protocol.network.types.game.context.fight.FightResultAdditionalData;

@SuppressWarnings("unused")
public class FightResultExperienceData extends FightResultAdditionalData {
	public static final int ProtocolId = 192;

	public long experience;
	public boolean showExperience;
	public long experienceLevelFloor;
	public boolean showExperienceLevelFloor;
	public long experienceNextLevelFloor;
	public boolean showExperienceNextLevelFloor;
	public long experienceFightDelta;
	public boolean showExperienceFightDelta;
	public long experienceForGuild;
	public boolean showExperienceForGuild;
	public long experienceForMount;
	public boolean showExperienceForMount;
	public boolean isIncarnationExperience;
	public int rerollExperienceMul;

	public FightResultExperienceData(){
	}

	public FightResultExperienceData(long experience, boolean showExperience, long experienceLevelFloor, boolean showExperienceLevelFloor, long experienceNextLevelFloor, boolean showExperienceNextLevelFloor, long experienceFightDelta, boolean showExperienceFightDelta, long experienceForGuild, boolean showExperienceForGuild, long experienceForMount, boolean showExperienceForMount, boolean isIncarnationExperience, int rerollExperienceMul){
		this.experience = experience;
		this.showExperience = showExperience;
		this.experienceLevelFloor = experienceLevelFloor;
		this.showExperienceLevelFloor = showExperienceLevelFloor;
		this.experienceNextLevelFloor = experienceNextLevelFloor;
		this.showExperienceNextLevelFloor = showExperienceNextLevelFloor;
		this.experienceFightDelta = experienceFightDelta;
		this.showExperienceFightDelta = showExperienceFightDelta;
		this.experienceForGuild = experienceForGuild;
		this.showExperienceForGuild = showExperienceForGuild;
		this.experienceForMount = experienceForMount;
		this.showExperienceForMount = showExperienceForMount;
		this.isIncarnationExperience = isIncarnationExperience;
		this.rerollExperienceMul = rerollExperienceMul;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, showExperience);
			flag = BooleanByteWrapper.SetFlag(1, flag, showExperienceLevelFloor);
			flag = BooleanByteWrapper.SetFlag(2, flag, showExperienceNextLevelFloor);
			flag = BooleanByteWrapper.SetFlag(3, flag, showExperienceFightDelta);
			flag = BooleanByteWrapper.SetFlag(4, flag, showExperienceForGuild);
			flag = BooleanByteWrapper.SetFlag(5, flag, showExperienceForMount);
			flag = BooleanByteWrapper.SetFlag(6, flag, isIncarnationExperience);
			writer.writeByte(flag);
			writer.writeVarLong(this.experience);
			writer.writeVarLong(this.experienceLevelFloor);
			writer.writeVarLong(this.experienceNextLevelFloor);
			writer.writeVarLong(this.experienceFightDelta);
			writer.writeVarLong(this.experienceForGuild);
			writer.writeVarLong(this.experienceForMount);
			writer.writeByte(this.rerollExperienceMul);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.showExperience = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.showExperienceLevelFloor = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.showExperienceNextLevelFloor = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.showExperienceFightDelta = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.showExperienceForGuild = BooleanByteWrapper.GetFlag(flag, (byte) 4);
			this.showExperienceForMount = BooleanByteWrapper.GetFlag(flag, (byte) 5);
			this.isIncarnationExperience = BooleanByteWrapper.GetFlag(flag, (byte) 6);
			this.experience = reader.readVarLong();
			this.experienceLevelFloor = reader.readVarLong();
			this.experienceNextLevelFloor = reader.readVarLong();
			this.experienceFightDelta = reader.readVarLong();
			this.experienceForGuild = reader.readVarLong();
			this.experienceForMount = reader.readVarLong();
			this.rerollExperienceMul = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("experience : " + this.experience);
		//Network.appendDebug("showExperience : " + this.showExperience);
		//Network.appendDebug("experienceLevelFloor : " + this.experienceLevelFloor);
		//Network.appendDebug("showExperienceLevelFloor : " + this.showExperienceLevelFloor);
		//Network.appendDebug("experienceNextLevelFloor : " + this.experienceNextLevelFloor);
		//Network.appendDebug("showExperienceNextLevelFloor : " + this.showExperienceNextLevelFloor);
		//Network.appendDebug("experienceFightDelta : " + this.experienceFightDelta);
		//Network.appendDebug("showExperienceFightDelta : " + this.showExperienceFightDelta);
		//Network.appendDebug("experienceForGuild : " + this.experienceForGuild);
		//Network.appendDebug("showExperienceForGuild : " + this.showExperienceForGuild);
		//Network.appendDebug("experienceForMount : " + this.experienceForMount);
		//Network.appendDebug("showExperienceForMount : " + this.showExperienceForMount);
		//Network.appendDebug("isIncarnationExperience : " + this.isIncarnationExperience);
		//Network.appendDebug("rerollExperienceMul : " + this.rerollExperienceMul);
	//}
}