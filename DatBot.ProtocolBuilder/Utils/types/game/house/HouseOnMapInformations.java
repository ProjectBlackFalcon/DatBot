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
import protocol.network.types.game.house.HouseInstanceInformations;

@SuppressWarnings("unused")
public class HouseOnMapInformations extends HouseInformations {
	public static final int ProtocolId = 510;

	private List<Integer> doorsOnMap;
	private List<HouseInstanceInformations> houseInstances;

	public List<Integer> getDoorsOnMap() { return this.doorsOnMap; }
	public void setDoorsOnMap(List<Integer> doorsOnMap) { this.doorsOnMap = doorsOnMap; };
	public List<HouseInstanceInformations> getHouseInstances() { return this.houseInstances; }
	public void setHouseInstances(List<HouseInstanceInformations> houseInstances) { this.houseInstances = houseInstances; };

	public HouseOnMapInformations(){
	}

	public HouseOnMapInformations(List<Integer> doorsOnMap, List<HouseInstanceInformations> houseInstances){
		this.doorsOnMap = doorsOnMap;
		this.houseInstances = houseInstances;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.doorsOnMap.size());
			int _loc2_ = 0;
			while( _loc2_ < this.doorsOnMap.size()){
				writer.writeInt(this.doorsOnMap.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.houseInstances.size());
			int _loc3_ = 0;
			while( _loc3_ < this.houseInstances.size()){
				this.houseInstances.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.doorsOnMap = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.doorsOnMap.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.houseInstances = new ArrayList<HouseInstanceInformations>();
			while( _loc5_ <  _loc4_){
				HouseInstanceInformations _loc16_ = new HouseInstanceInformations();
				_loc16_.Deserialize(reader);
				this.houseInstances.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
