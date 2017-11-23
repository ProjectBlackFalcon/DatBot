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

	public int instanceId;
	public boolean secondHand;
	public String ownerName;
	public int worldX;
	public int worldY;
	public double mapId;
	public int subAreaId;
	public List<Integer> skillListIds;
	public int guildshareParams;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
		//Network.appendDebug("ownerName : " + this.ownerName);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("mapId : " + this.mapId);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//for(Integer a : skillListIds) {
			//Network.appendDebug("skillListIds : " + a);
		//}
		//Network.appendDebug("guildshareParams : " + this.guildshareParams);
	//}
}
