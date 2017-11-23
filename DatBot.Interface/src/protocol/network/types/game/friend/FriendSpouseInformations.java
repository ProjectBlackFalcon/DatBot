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

	public int spouseAccountId;
	public long spouseId;
	public String spouseName;
	public int spouseLevel;
	public int breed;
	public int sex;
	public EntityLook spouseEntityLook;
	public GuildInformations guildInfo;
	public int alignmentSide;

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
			writer.writeByte(this.spouseLevel);
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
			this.spouseLevel = reader.readByte();
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
		//append();
	}

	//private void append(){
		//Network.appendDebug("spouseAccountId : " + this.spouseAccountId);
		//Network.appendDebug("spouseId : " + this.spouseId);
		//Network.appendDebug("spouseName : " + this.spouseName);
		//Network.appendDebug("spouseLevel : " + this.spouseLevel);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("spouseEntityLook : " + this.spouseEntityLook);
		//Network.appendDebug("guildInfo : " + this.guildInfo);
		//Network.appendDebug("alignmentSide : " + this.alignmentSide);
	//}
}
