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

	public int instanceId;
	public boolean secondHand;
	public int modelId;
	public String ownerName;
	public boolean ownerConnected;
	public int worldX;
	public int worldY;
	public int subAreaId;
	public int nbRoom;
	public int nbChest;
	public List<Integer> skillListIds;
	public boolean isLocked;
	public long price;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
		//Network.appendDebug("modelId : " + this.modelId);
		//Network.appendDebug("ownerName : " + this.ownerName);
		//Network.appendDebug("ownerConnected : " + this.ownerConnected);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("nbRoom : " + this.nbRoom);
		//Network.appendDebug("nbChest : " + this.nbChest);
		//for(Integer a : skillListIds) {
			//Network.appendDebug("skillListIds : " + a);
		//}
		//Network.appendDebug("isLocked : " + this.isLocked);
		//Network.appendDebug("price : " + this.price);
	//}
}
