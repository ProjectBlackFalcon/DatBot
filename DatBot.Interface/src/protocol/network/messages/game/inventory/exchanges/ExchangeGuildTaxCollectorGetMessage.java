package protocol.network.messages.game.inventory.exchanges;

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
import protocol.network.types.game.data.items.ObjectItemGenericQuantity;

@SuppressWarnings("unused")
public class ExchangeGuildTaxCollectorGetMessage extends NetworkMessage {
	public static final int ProtocolId = 5762;

	public String collectorName;
	public int worldX;
	public int worldY;
	public double mapId;
	public int subAreaId;
	public String userName;
	public long callerId;
	public String callerName;
	public double experience;
	public int pods;
	public List<ObjectItemGenericQuantity> objectsInfos;

	public ExchangeGuildTaxCollectorGetMessage(){
	}

	public ExchangeGuildTaxCollectorGetMessage(String collectorName, int worldX, int worldY, double mapId, int subAreaId, String userName, long callerId, String callerName, double experience, int pods, List<ObjectItemGenericQuantity> objectsInfos){
		this.collectorName = collectorName;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.userName = userName;
		this.callerId = callerId;
		this.callerName = callerName;
		this.experience = experience;
		this.pods = pods;
		this.objectsInfos = objectsInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.collectorName);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
			writer.writeUTF(this.userName);
			writer.writeVarLong(this.callerId);
			writer.writeUTF(this.callerName);
			writer.writeDouble(this.experience);
			writer.writeVarShort(this.pods);
			writer.writeShort(this.objectsInfos.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectsInfos.size()){
				this.objectsInfos.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.collectorName = reader.readUTF();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
			this.userName = reader.readUTF();
			this.callerId = reader.readVarLong();
			this.callerName = reader.readUTF();
			this.experience = reader.readDouble();
			this.pods = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objectsInfos = new ArrayList<ObjectItemGenericQuantity>();
			while( _loc3_ <  _loc2_){
				ObjectItemGenericQuantity _loc15_ = new ObjectItemGenericQuantity();
				_loc15_.Deserialize(reader);
				this.objectsInfos.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("collectorName : " + this.collectorName);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("mapId : " + this.mapId);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("userName : " + this.userName);
		//Network.appendDebug("callerId : " + this.callerId);
		//Network.appendDebug("callerName : " + this.callerName);
		//Network.appendDebug("experience : " + this.experience);
		//Network.appendDebug("pods : " + this.pods);
		//for(ObjectItemGenericQuantity a : objectsInfos) {
			//Network.appendDebug("objectsInfos : " + a);
		//}
	//}
}
