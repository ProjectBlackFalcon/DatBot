package protocol.network.types.game.paddock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.paddock.PaddockInformations;
import protocol.network.types.game.paddock.PaddockBuyableInformations;

@SuppressWarnings("unused")
public class PaddockInstancesInformations extends PaddockInformations {
	public static final int ProtocolId = 509;

	private List<PaddockBuyableInformations> paddocks;

	public List<PaddockBuyableInformations> getPaddocks() { return this.paddocks; }
	public void setPaddocks(List<PaddockBuyableInformations> paddocks) { this.paddocks = paddocks; };

	public PaddockInstancesInformations(){
	}

	public PaddockInstancesInformations(List<PaddockBuyableInformations> paddocks){
		this.paddocks = paddocks;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.paddocks.size());
			int _loc2_ = 0;
			while( _loc2_ < this.paddocks.size()){
				writer.writeShort(PaddockBuyableInformations.ProtocolId);
				this.paddocks.get(_loc2_).Serialize(writer);
				_loc2_++;
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
			this.paddocks = new ArrayList<PaddockBuyableInformations>();
			while( _loc3_ <  _loc2_){
				PaddockBuyableInformations _loc15_ = (PaddockBuyableInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.paddocks.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
