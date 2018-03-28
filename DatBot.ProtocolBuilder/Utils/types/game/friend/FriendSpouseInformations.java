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
import protocol.network.NetworkMessage;
import protocol.network.types.game.look.EntityLook;
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class FriendSpouseInformations extends NetworkMessage {
	public static final int ProtocolId = 77;

	private int spouseAccountId;
	private long spouseId;
	private String spouseName;
	private int spouseLevel;
	private int breed;
	private int sex;
	private EntityLook spouseEntityLook;
	private GuildInformations guildInfo;
	private int alignmentSide;

	public int getSpouseAccountId() { return this.spouseAccountId; }
	public void setSpouseAccountId(int spouseAccountId) { this.spouseAccountId = spouseAccountId; };
	public long getSpouseId() { return this.spouseId; }
	public void setSpouseId(long spouseId) { this.spouseId = spouseId; };
	public String getSpouseName() { return this.spouseName; }
	public void setSpouseName(String spouseName) { this.spouseName = spouseName; };
	public int getSpouseLevel() { return this.spouseLevel; }
	public void setSpouseLevel(int spouseLevel) { this.spouseLevel = spouseLevel; };
	public int getBreed() { return this.breed; }
	public void setBreed(int breed) { this.breed = breed; };
	public int getSex() { return this.sex; }
	public void setSex(int sex) { this.sex = sex; };
	public EntityLook getSpouseEntityLook() { return this.spouseEntityLook; }
	public void setSpouseEntityLook(EntityLook spouseEntityLook) { this.spouseEntityLook = spouseEntityLook; };
	public GuildInformations getGuildInfo() { return this.guildInfo; }
	public void setGuildInfo(GuildInformations guildInfo) { this.guildInfo = guildInfo; };
	public int getAlignmentSide() { return this.alignmentSide; }
	public void setAlignmentSide(int alignmentSide) { this.alignmentSide = alignmentSide; };

	public FriendSpouseInformations(){
	}

	public FriendSpouseInformations(int spouseAccountId, long spouseId, String spouseName, int spouseLevel, int breed, int sex, EntityLook spouseEntityLook, GuildInformations guildInfo, int alignmentSide){
		this.spouseAccountId = spouseAccountId;
		this.spouseId = spouseId;
		this.spouseName = spouseName;
		this.spouseLevel = spouseLevel;
		this.breed = breed;
		this.sex = sex;
		this.spouseEntityLook = spouseEntityLook;
		this.guildInfo = guildInfo;
		this.alignmentSide = alignmentSide;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.spouseAccountId);
			writer.writeVarLong(this.spouseId);
			writer.writeUTF(this.spouseName);
			writer.writeVarShort(this.spouseLevel);
			writer.writeByte(this.breed);
			writer.writeByte(this.sex);
			spouseEntityLook.Serialize(writer);
			guildInfo.Serialize(writer);
			writer.writeByte(this.alignmentSide);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spouseAccountId = reader.readInt();
			this.spouseId = reader.readVarLong();
			this.spouseName = reader.readUTF();
			this.spouseLevel = reader.readVarShort();
			this.breed = reader.readByte();
			this.sex = reader.readByte();
			this.spouseEntityLook = new EntityLook();
			this.spouseEntityLook.Deserialize(reader);
			this.guildInfo = new GuildInformations();
			this.guildInfo.Deserialize(reader);
			this.alignmentSide = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
