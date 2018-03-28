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

	private long experience;
	private boolean showExperience;
	private long experienceLevelFloor;
	private boolean showExperienceLevelFloor;
	private long experienceNextLevelFloor;
	private boolean showExperienceNextLevelFloor;
	private long experienceFightDelta;
	private boolean showExperienceFightDelta;
	private long experienceForGuild;
	private boolean showExperienceForGuild;
	private long experienceForMount;
	private boolean showExperienceForMount;
	private boolean isIncarnationExperience;
	private int rerollExperienceMul;

	public long getExperience() { return this.experience; }
	public void setExperience(long experience) { this.experience = experience; };
	public boolean isShowExperience() { return this.showExperience; }
	public void setShowExperience(boolean showExperience) { this.showExperience = showExperience; };
	public long getExperienceLevelFloor() { return this.experienceLevelFloor; }
	public void setExperienceLevelFloor(long experienceLevelFloor) { this.experienceLevelFloor = experienceLevelFloor; };
	public boolean isShowExperienceLevelFloor() { return this.showExperienceLevelFloor; }
	public void setShowExperienceLevelFloor(boolean showExperienceLevelFloor) { this.showExperienceLevelFloor = showExperienceLevelFloor; };
	public long getExperienceNextLevelFloor() { return this.experienceNextLevelFloor; }
	public void setExperienceNextLevelFloor(long experienceNextLevelFloor) { this.experienceNextLevelFloor = experienceNextLevelFloor; };
	public boolean isShowExperienceNextLevelFloor() { return this.showExperienceNextLevelFloor; }
	public void setShowExperienceNextLevelFloor(boolean showExperienceNextLevelFloor) { this.showExperienceNextLevelFloor = showExperienceNextLevelFloor; };
	public long getExperienceFightDelta() { return this.experienceFightDelta; }
	public void setExperienceFightDelta(long experienceFightDelta) { this.experienceFightDelta = experienceFightDelta; };
	public boolean isShowExperienceFightDelta() { return this.showExperienceFightDelta; }
	public void setShowExperienceFightDelta(boolean showExperienceFightDelta) { this.showExperienceFightDelta = showExperienceFightDelta; };
	public long getExperienceForGuild() { return this.experienceForGuild; }
	public void setExperienceForGuild(long experienceForGuild) { this.experienceForGuild = experienceForGuild; };
	public boolean isShowExperienceForGuild() { return this.showExperienceForGuild; }
	public void setShowExperienceForGuild(boolean showExperienceForGuild) { this.showExperienceForGuild = showExperienceForGuild; };
	public long getExperienceForMount() { return this.experienceForMount; }
	public void setExperienceForMount(long experienceForMount) { this.experienceForMount = experienceForMount; };
	public boolean isShowExperienceForMount() { return this.showExperienceForMount; }
	public void setShowExperienceForMount(boolean showExperienceForMount) { this.showExperienceForMount = showExperienceForMount; };
	public boolean isIsIncarnationExperience() { return this.isIncarnationExperience; }
	public void setIsIncarnationExperience(boolean isIncarnationExperience) { this.isIncarnationExperience = isIncarnationExperience; };
	public int getRerollExperienceMul() { return this.rerollExperienceMul; }
	public void setRerollExperienceMul(int rerollExperienceMul) { this.rerollExperienceMul = rerollExperienceMul; };

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
	}

}
