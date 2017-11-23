package protocol.network.messages.game.context.roleplay.houses;

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
import protocol.network.types.game.house.HouseInstanceInformations;

@SuppressWarnings("unused")
public class HousePropertiesMessage extends NetworkMessage {
	public static final int ProtocolId = 5734;

	public int houseId;
	public List<Integer> doorsOnMap;
	public HouseInstanceInformations properties;

	public HousePropertiesMessage(){
	}

	public HousePropertiesMessage(int houseId, List<Integer> doorsOnMap, HouseInstanceInformations properties){
		this.houseId = houseId;
		this.doorsOnMap = doorsOnMap;
		this.properties = properties;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.houseId);
			writer.writeShort(this.doorsOnMap.size());
			int _loc2_ = 0;
			while( _loc2_ < this.doorsOnMap.size()){
				writer.writeInt(this.doorsOnMap.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(HouseInstanceInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.houseId = reader.readVarInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.doorsOnMap = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.doorsOnMap.add(_loc15_);
				_loc3_++;
			}
			this.properties = (HouseInstanceInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.properties.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("houseId : " + this.houseId);
		//for(Integer a : doorsOnMap) {
			//Network.appendDebug("doorsOnMap : " + a);
		//}
		//Network.appendDebug("properties : " + this.properties);
	//}
}
