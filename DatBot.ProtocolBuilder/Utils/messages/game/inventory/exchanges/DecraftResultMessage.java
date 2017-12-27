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
import protocol.network.types.game.context.roleplay.job.DecraftedItemStackInfo;

@SuppressWarnings("unused")
public class DecraftResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6569;

	private List<DecraftedItemStackInfo> results;

	public List<DecraftedItemStackInfo> getResults() { return this.results; };
	public void setResults(List<DecraftedItemStackInfo> results) { this.results = results; };

	public DecraftResultMessage(){
	}

	public DecraftResultMessage(List<DecraftedItemStackInfo> results){
		this.results = results;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.results.size());
			int _loc2_ = 0;
			while( _loc2_ < this.results.size()){
				this.results.get(_loc2_).Serialize(writer);
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
			this.results = new ArrayList<DecraftedItemStackInfo>();
			while( _loc3_ <  _loc2_){
				DecraftedItemStackInfo _loc15_ = new DecraftedItemStackInfo();
				_loc15_.Deserialize(reader);
				this.results.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
