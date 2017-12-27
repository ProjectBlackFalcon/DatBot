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
import protocol.network.types.game.mount.UpdateMountBoost;

@SuppressWarnings("unused")
public class UpdateMountBoostMessage extends NetworkMessage {
	public static final int ProtocolId = 6179;

	private int rideId;
	private List<UpdateMountBoost> boostToUpdateList;

	public int getRideId() { return this.rideId; };
	public void setRideId(int rideId) { this.rideId = rideId; };
	public List<UpdateMountBoost> getBoostToUpdateList() { return this.boostToUpdateList; };
	public void setBoostToUpdateList(List<UpdateMountBoost> boostToUpdateList) { this.boostToUpdateList = boostToUpdateList; };

	public UpdateMountBoostMessage(){
	}

	public UpdateMountBoostMessage(int rideId, List<UpdateMountBoost> boostToUpdateList){
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
				writer.writeShort(UpdateMountBoost.ProtocolId);
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
			this.boostToUpdateList = new ArrayList<UpdateMountBoost>();
			while( _loc3_ <  _loc2_){
				UpdateMountBoost _loc15_ = (UpdateMountBoost) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.boostToUpdateList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
