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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class HouseInformationsForSell extends NetworkMessage {
	public static final int ProtocolId = 221;

	private int instanceId;
	private boolean secondHand;
	private int modelId;
	private String ownerName;
	private boolean ownerConnected;
	private int worldX;
	private int worldY;
	private int subAreaId;
	private int nbRoom;
	private int nbChest;
	private List<Integer> skillListIds;
	private boolean isLocked;
	private long price;

	public int getInstanceId() { return this.instanceId; }
	public void setInstanceId(int instanceId) { this.instanceId = instanceId; };
	public boolean isSecondHand() { return this.secondHand; }
	public void setSecondHand(boolean secondHand) { this.secondHand = secondHand; };
	public int getModelId() { return this.modelId; }
	public void setModelId(int modelId) { this.modelId = modelId; };
	public String getOwnerName() { return this.ownerName; }
	public void setOwnerName(String ownerName) { this.ownerName = ownerName; };
	public boolean isOwnerConnected() { return this.ownerConnected; }
	public void setOwnerConnected(boolean ownerConnected) { this.ownerConnected = ownerConnected; };
	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getNbRoom() { return this.nbRoom; }
	public void setNbRoom(int nbRoom) { this.nbRoom = nbRoom; };
	public int getNbChest() { return this.nbChest; }
	public void setNbChest(int nbChest) { this.nbChest = nbChest; };
	public List<Integer> getSkillListIds() { return this.skillListIds; }
	public void setSkillListIds(List<Integer> skillListIds) { this.skillListIds = skillListIds; };
	public boolean isIsLocked() { return this.isLocked; }
	public void setIsLocked(boolean isLocked) { this.isLocked = isLocked; };
	public long getPrice() { return this.price; }
	public void setPrice(long price) { this.price = price; };

	public HouseInformationsForSell(){
	}

	public HouseInformationsForSell(int instanceId, boolean secondHand, int modelId, String ownerName, boolean ownerConnected, int worldX, int worldY, int subAreaId, int nbRoom, int nbChest, List<Integer> skillListIds, boolean isLocked, long price){
		this.instanceId = instanceId;
		this.secondHand = secondHand;
		this.modelId = modelId;
		this.ownerName = ownerName;
		this.ownerConnected = ownerConnected;
		this.worldX = worldX;
		this.worldY = worldY;
		this.subAreaId = subAreaId;
		this.nbRoom = nbRoom;
		this.nbChest = nbChest;
		this.skillListIds = skillListIds;
		this.isLocked = isLocked;
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.instanceId);
			writer.writeBoolean(this.secondHand);
			writer.writeVarInt(this.modelId);
			writer.writeUTF(this.ownerName);
			writer.writeBoolean(this.ownerConnected);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeVarShort(this.subAreaId);
			writer.writeByte(this.nbRoom);
			writer.writeByte(this.nbChest);
			writer.writeShort(this.skillListIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.skillListIds.size()){
				writer.writeInt(this.skillListIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeBoolean(this.isLocked);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.instanceId = reader.readInt();
			this.secondHand = reader.readBoolean();
			this.modelId = reader.readVarInt();
			this.ownerName = reader.readUTF();
			this.ownerConnected = reader.readBoolean();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.subAreaId = reader.readVarShort();
			this.nbRoom = reader.readByte();
			this.nbChest = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.skillListIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.skillListIds.add(_loc15_);
				_loc3_++;
			}
			this.isLocked = reader.readBoolean();
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
