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
import protocol.network.types.game.mount.UpdateMountCharacteristic;

@SuppressWarnings("unused")
public class UpdateMountCharacteristicsMessage extends NetworkMessage {
	public static final int ProtocolId = 6753;

	private int rideId;
	private List<UpdateMountCharacteristic> boostToUpdateList;

	public int getRideId() { return this.rideId; }
	public void setRideId(int rideId) { this.rideId = rideId; };
	public List<UpdateMountCharacteristic> getBoostToUpdateList() { return this.boostToUpdateList; }
	public void setBoostToUpdateList(List<UpdateMountCharacteristic> boostToUpdateList) { this.boostToUpdateList = boostToUpdateList; };

	public UpdateMountCharacteristicsMessage(){
	}

	public UpdateMountCharacteristicsMessage(int rideId, List<UpdateMountCharacteristic> boostToUpdateList){
		this.rideId = rideId;
		this.boostToUpdateList = boostToUpdateList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.rideId);
			writer.writeShort(this.boostToUpdateList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.boostToUpdateList.size()){
				writer.writeShort(UpdateMountCharacteristic.ProtocolId);
				this.boostToUpdateList.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.rideId = reader.readVarInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.boostToUpdateList = new ArrayList<UpdateMountCharacteristic>();
			while( _loc3_ <  _loc2_){
				UpdateMountCharacteristic _loc15_ = (UpdateMountCharacteristic) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.boostToUpdateList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
