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
public class ExchangeStartOkJobIndexMessage extends NetworkMessage {
	public static final int ProtocolId = 5819;

	private List<Integer> jobs;

	public List<Integer> getJobs() { return this.jobs; }
	public void setJobs(List<Integer> jobs) { this.jobs = jobs; };

	public ExchangeStartOkJobIndexMessage(){
	}

	public ExchangeStartOkJobIndexMessage(List<Integer> jobs){
		this.jobs = jobs;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.jobs.size());
			int _loc2_ = 0;
			while( _loc2_ < this.jobs.size()){
				writer.writeVarInt(this.jobs.get(_loc2_));
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
			this.jobs = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.jobs.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
