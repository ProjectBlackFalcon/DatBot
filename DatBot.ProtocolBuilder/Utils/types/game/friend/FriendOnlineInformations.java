package protocol.network.types.game.friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.friend.FriendInformations;
import protocol.network.types.game.context.roleplay.GuildInformations;
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class FriendOnlineInformations extends FriendInformations {
	public static final int ProtocolId = 92;

	private long playerId;
	private String playerName;
	private int level;
	private int alignmentSide;
	private int breed;
	private boolean sex;
	private GuildInformations guildInfo;
	private int moodSmileyId;
	private PlayerStatus status;
	private boolean havenBagShared;

	public long getPlayerId() { return this.playerId; };
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public String getPlayerName() { return this.playerName; };
	public void setPlayerName(String playerName) { this.playerName = playerName; };
	public int getLevel() { return this.level; };
	public void setLevel(int level) { this.level = level; };
	public int getAlignmentSide() { return this.alignmentSide; };
	public void setAlignmentSide(int alignmentSide) { this.alignmentSide = alignmentSide; };
	public int getBreed() { return this.breed; };
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; };
	public void setSex(boolean sex) { this.sex = sex; };
	public GuildInformations getGuildInfo() { return this.guildInfo; };
	public void setGuildInfo(GuildInformations guildInfo) { this.guildInfo = guildInfo; };
	public int getMoodSmileyId() { return this.moodSmileyId; };
	public void setMoodSmileyId(int moodSmileyId) { this.moodSmileyId = moodSmileyId; };
	public PlayerStatus getStatus() { return this.status; };
	public void setStatus(PlayerStatus status) { this.status = status; };
	public boolean isHavenBagShared() { return this.havenBagShared; };
	public void setHavenBagShared(boolean havenBagShared) { this.havenBagShared = havenBagShared; };

	public FriendOnlineInformations(){
	}

	public FriendOnlineInformations(long playerId, String playerName, int level, int alignmentSide, int breed, boolean sex, GuildInformations guildInfo, int moodSmileyId, PlayerStatus status, boolean havenBagShared){
		this.playerId = playerId;
		this.playerName = playerName;
		this.level = level;
		this.alignmentSide = alignmentSide;
		this.breed = breed;
		this.sex = sex;
		this.guildInfo = guildInfo;
		this.moodSmileyId = moodSmileyId;
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
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
			writer.writeVarShort(this.level);
			writer.writeByte(this.alignmentSide);
			writer.writeByte(this.breed);
			guildInfo.Serialize(writer);
			writer.writeVarShort(this.moodSmileyId);
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
			this.playerId = reader.readVarLong();
			this.playerName = reader.readUTF();
			this.level = reader.readVarShort();
			this.alignmentSide = reader.readByte();
			this.breed = reader.readByte();
			this.guildInfo = new GuildInformations();
			this.guildInfo.Deserialize(reader);
			this.moodSmileyId = reader.readVarShort();
			this.status = (PlayerStatus) ProtocolTypeManager.getInstance(reader.readShort());
			this.status.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
