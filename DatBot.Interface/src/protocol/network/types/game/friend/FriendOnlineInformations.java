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

	public long playerId;
	public String playerName;
	public int level;
	public int alignmentSide;
	public int breed;
	public boolean sex;
	public GuildInformations guildInfo;
	public int moodSmileyId;
	public PlayerStatus status;
	public boolean havenBagShared;

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
			writer.writeByte(this.level);
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
			this.level = reader.readByte();
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
		//append();
	}

	//private void append(){
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("playerName : " + this.playerName);
		//Network.appendDebug("level : " + this.level);
		//Network.appendDebug("alignmentSide : " + this.alignmentSide);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("guildInfo : " + this.guildInfo);
		//Network.appendDebug("moodSmileyId : " + this.moodSmileyId);
		//Network.appendDebug("status : " + this.status);
		//Network.appendDebug("havenBagShared : " + this.havenBagShared);
	//}
}