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

	private long playerId;
	private String playerName;
	private int alignmentSide;
	private int breed;
	private boolean sex;
	private boolean isInWorkshop;
	private int worldX;
	private int worldY;
	private double mapId;
	private int subAreaId;
	private PlayerStatus status;

	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public String getPlayerName() { return this.playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; };
	public int getAlignmentSide() { return this.alignmentSide; }
	public void setAlignmentSide(int alignmentSide) { this.alignmentSide = alignmentSide; };
	public int getBreed() { return this.breed; }
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; }
	public void setSex(boolean sex) { this.sex = sex; };
	public boolean isIsInWorkshop() { return this.isInWorkshop; }
	public void setIsInWorkshop(boolean isInWorkshop) { this.isInWorkshop = isInWorkshop; };
	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public PlayerStatus getStatus() { return this.status; }
	public void setStatus(PlayerStatus status) { this.status = status; };

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
	}

}
