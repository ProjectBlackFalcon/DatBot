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

@SuppressWarnings("unused")
public class JobBookSubscribeRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6592;

	private List<Integer> jobIds;

	public List<Integer> getJobIds() { return this.jobIds; }
	public void setJobIds(List<Integer> jobIds) { this.jobIds = jobIds; };

	public JobBookSubscribeRequestMessage(){
	}

	public JobBookSubscribeRequestMessage(List<Integer> jobIds){
		this.jobIds = jobIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.jobIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.jobIds.size()){
				writer.writeByte(this.jobIds.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.jobIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.jobIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
