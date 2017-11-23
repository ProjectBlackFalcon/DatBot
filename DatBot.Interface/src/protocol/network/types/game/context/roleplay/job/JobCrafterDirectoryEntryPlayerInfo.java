package protocol.network.types.game.context.roleplay.job;

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
import protocol.network.types.game.character.status.PlayerStatus;

@SuppressWarnings("unused")
public class JobCrafterDirectoryEntryPlayerInfo extends NetworkMessage {
	public static final int ProtocolId = 194;

	public long playerId;
	public String playerName;
	public int alignmentSide;
	public int breed;
	public boolean sex;
	public boolean isInWorkshop;
	public int worldX;
	public int worldY;
	public double mapId;
	public int subAreaId;
	public PlayerStatus status;

	public JobCrafterDirectoryEntryPlayerInfo(){
	}

	public JobCrafterDirectoryEntryPlayerInfo(long playerId, String playerName, int alignmentSide, int breed, boolean sex, boolean isInWorkshop, int worldX, int worldY, double mapId, int subAreaId, PlayerStatus status){
		this.playerId = playerId;
		this.playerName = playerName;
		this.alignmentSide = alignmentSide;
		this.breed = breed;
		this.sex = sex;
		this.isInWorkshop = isInWorkshop;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.status = status;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
			writer.writeByte(this.alignmentSide);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
			writer.writeBoolean(this.isInWorkshop);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
			writer.writeShort(PlayerStatus.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerId = reader.readVarLong();
			this.playerName = reader.readUTF();
			this.alignmentSide = reader.readByte();
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
			this.isInWorkshop = reader.readBoolean();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
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
		//Network.appendDebug("alignmentSide : " + this.alignmentSide);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("isInWorkshop : " + this.isInWorkshop);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("mapId : " + this.mapId);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("status : " + this.status);
	//}
}
