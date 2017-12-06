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

	public int breed;
	public boolean sex;
	public int rank;
	public long givenExperience;
	public int experienceGivenPercent;
	public int rights;
	public int connected;
	public int alignmentSide;
	public int hoursSinceLastConnection;
	public int moodSmileyId;
	public int accountId;
	public int achievementPoints;
	public PlayerStatus status;
	public boolean havenBagShared;

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

	//private void append(){
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("rank : " + this.rank);
		//Network.appendDebug("givenExperience : " + this.givenExperience);
		//Network.appendDebug("experienceGivenPercent : " + this.experienceGivenPercent);
		//Network.appendDebug("rights : " + this.rights);
		//Network.appendDebug("connected : " + this.connected);
		//Network.appendDebug("alignmentSide : " + this.alignmentSide);
		//Network.appendDebug("hoursSinceLastConnection : " + this.hoursSinceLastConnection);
		//Network.appendDebug("moodSmileyId : " + this.moodSmileyId);
		//Network.appendDebug("accountId : " + this.accountId);
		//Network.appendDebug("achievementPoints : " + this.achievementPoints);
		//Network.appendDebug("status : " + this.status);
		//Network.appendDebug("havenBagShared : " + this.havenBagShared);
	//}
}
