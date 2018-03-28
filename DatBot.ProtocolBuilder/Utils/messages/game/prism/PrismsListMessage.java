package protocol.network.messages.game.prism;

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
import protocol.network.types.game.prism.PrismSubareaEmptyInfo;

@SuppressWarnings("unused")
public class PrismsListMessage extends NetworkMessage {
	public static final int ProtocolId = 6440;

	private List<PrismSubareaEmptyInfo> prisms;

	public List<PrismSubareaEmptyInfo> getPrisms() { return this.prisms; }
	public void setPrisms(List<PrismSubareaEmptyInfo> prisms) { this.prisms = prisms; };

	public PrismsListMessage(){
	}

	public PrismsListMessage(List<PrismSubareaEmptyInfo> prisms){
		this.prisms = prisms;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.prisms.size());
			int _loc2_ = 0;
			while( _loc2_ < this.prisms.size()){
				writer.writeShort(PrismSubareaEmptyInfo.ProtocolId);
				this.prisms.get(_loc2_).Serialize(writer);
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
			this.prisms = new ArrayList<PrismSubareaEmptyInfo>();
			while( _loc3_ <  _loc2_){
				PrismSubareaEmptyInfo _loc15_ = (PrismSubareaEmptyInfo) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.prisms.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
