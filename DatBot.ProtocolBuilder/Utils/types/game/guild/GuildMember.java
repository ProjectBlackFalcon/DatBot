package protocol.network.types.game.guild;

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
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class GuildMember extends CharacterMinimalInformations {
	public static final int ProtocolId = 88;

	private int breed;
	private boolean sex;
	private int rank;
	private long givenExperience;
	private int experienceGivenPercent;
	private int rights;
	private int connected;
	private int alignmentSide;
	private int hoursSinceLastConnection;
	private int moodSmileyId;
	private int accountId;
	private int achievementPoints;
	private PlayerStatus status;
	private boolean havenBagShared;

	public int getBreed() { return this.breed; };
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; };
	public void setSex(boolean sex) { this.sex = sex; };
	public int getRank() { return this.rank; };
	public void setRank(int rank) { this.rank = rank; };
	public long getGivenExperience() { return this.givenExperience; };
	public void setGivenExperience(long givenExperience) { this.givenExperience = givenExperience; };
	public int getExperienceGivenPercent() { return this.experienceGivenPercent; };
	public void setExperienceGivenPercent(int experienceGivenPercent) { this.experienceGivenPercent = experienceGivenPercent; };
	public int getRights() { return this.rights; };
	public void setRights(int rights) { this.rights = rights; };
	public int getConnected() { return this.connected; };
	public void setConnected(int connected) { this.connected = connected; };
	public int getAlignmentSide() { return this.alignmentSide; };
	public void setAlignmentSide(int alignmentSide) { this.alignmentSide = alignmentSide; };
	public int getHoursSinceLastConnection() { return this.hoursSinceLastConnection; };
	public void setHoursSinceLastConnection(int hoursSinceLastConnection) { this.hoursSinceLastConnection = hoursSinceLastConnection; };
	public int getMoodSmileyId() { return this.moodSmileyId; };
	public void setMoodSmileyId(int moodSmileyId) { this.moodSmileyId = moodSmileyId; };
	public int getAccountId() { return this.accountId; };
	public void setAccountId(int accountId) { this.accountId = accountId; };
	public int getAchievementPoints() { return this.achievementPoints; };
	public void setAchievementPoints(int achievementPoints) { this.achievementPoints = achievementPoints; };
	public PlayerStatus getStatus() { return this.status; };
	public void setStatus(PlayerStatus status) { this.status = status; };
	public boolean isHavenBagShared() { return this.havenBagShared; };
	public void setHavenBagShared(boolean havenBagShared) { this.havenBagShared = havenBagShared; };

	public GuildMember(){
	}

	public GuildMember(int breed, boolean sex, int rank, long givenExperience, int experienceGivenPercent, int rights, int connected, int alignmentSide, int hoursSinceLastConnection, int moodSmileyId, int accountId, int achievementPoints, PlayerStatus status, boolean havenBagShared){
		this.breed = breed;
		this.sex = sex;
		this.rank = rank;
		this.givenExperience = givenExperience;
		this.experienceGivenPercent = experienceGivenPercent;
		this.rights = rights;
		this.connected = connected;
		this.alignmentSide = alignmentSide;
		this.hoursSinceLastConnection = hoursSinceLastConnection;
		this.moodSmileyId = moodSmileyId;
		this.accountId = accountId;
		this.achievementPoints = achievementPoints;
		this.status = status;
		this.havenBagShared = havenBagShared;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, sex);
			flag = BooleanByteWrapper.SetFlag(1, flag, havenBagShared);
			writer.writeByte(flag);
			writer.writeByte(this.breed);
			writer.writeVarShort(this.rank);
			writer.writeVarLong(this.givenExperience);
			writer.writeByte(this.experienceGivenPercent);
			writer.writeVarInt(this.rights);
			writer.writeByte(this.connected);
			writer.writeByte(this.alignmentSide);
			writer.writeShort(this.hoursSinceLastConnection);
			writer.writeVarShort(this.moodSmileyId);
			writer.writeInt(this.accountId);
			writer.writeInt(this.achievementPoints);
			writer.writeShort(PlayerStatus.ProtocolId);
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
			this.sex = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.havenBagShared = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.breed = reader.readByte();
			this.rank = reader.readVarShort();
			this.givenExperience = reader.readVarLong();
			this.experienceGivenPercent = reader.readByte();
			this.rights = reader.readVarInt();
			this.connected = reader.readByte();
			this.alignmentSide = reader.readByte();
			this.hoursSinceLastConnection = reader.readShort();
			this.moodSmileyId = reader.readVarShort();
			this.accountId = reader.readInt();
			this.achievementPoints = reader.readInt();
			this.status = (PlayerStatus) ProtocolTypeManager.getInstance(reader.readShort());
			this.status.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
