package protocol.network.types.game.house;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.house.HouseInformations;

@SuppressWarnings("unused")
public class HouseInformationsForGuild extends HouseInformations {
	public static final int ProtocolId = 170;

	private int instanceId;
	private boolean secondHand;
	private String ownerName;
	private int worldX;
	private int worldY;
	private double mapId;
	private int subAreaId;
	private List<Integer> skillListIds;
	private int guildshareParams;

	public int getInstanceId() { return this.instanceId; };
	public void setInstanceId(int instanceId) { this.instanceId = instanceId; };
	public boolean isSecondHand() { return this.secondHand; };
	public void setSecondHand(boolean secondHand) { this.secondHand = secondHand; };
	public String getOwnerName() { return this.ownerName; };
	public void setOwnerName(String ownerName) { this.ownerName = ownerName; };
	public int getWorldX() { return this.worldX; };
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; };
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; };
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public List<Integer> getSkillListIds() { return this.skillListIds; };
	public void setSkillListIds(List<Integer> skillListIds) { this.skillListIds = skillListIds; };
	public int getGuildshareParams() { return this.guildshareParams; };
	public void setGuildshareParams(int guildshareParams) { this.guildshareParams = guildshareParams; };

	public HouseInformationsForGuild(){
	}

	public HouseInformationsForGuild(int instanceId, boolean secondHand, String ownerName, int worldX, int worldY, double mapId, int subAreaId, List<Integer> skillListIds, int guildshareParams){
		this.instanceId = instanceId;
		this.secondHand = secondHand;
		this.ownerName = ownerName;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.skillListIds = skillListIds;
		this.guildshareParams = guildshareParams;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.instanceId);
			writer.writeBoolean(this.secondHand);
			writer.writeUTF(this.ownerName);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
			writer.writeShort(this.skillListIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.skillListIds.size()){
				writer.writeInt(this.skillListIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeVarInt(this.guildshareParams);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.instanceId = reader.readInt();
			this.secondHand = reader.readBoolean();
			this.ownerName = reader.readUTF();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.skillListIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.skillListIds.add(_loc15_);
				_loc3_++;
			}
			this.guildshareParams = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
