package protocol.network.messages.game.look;

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
public class AccessoryPreviewRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6518;

	private List<Integer> genericId;

	public List<Integer> getGenericId() { return this.genericId; }
	public void setGenericId(List<Integer> genericId) { this.genericId = genericId; };

	public AccessoryPreviewRequestMessage(){
	}

	public AccessoryPreviewRequestMessage(List<Integer> genericId){
		this.genericId = genericId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.genericId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.genericId.size()){
				writer.writeVarShort(this.genericId.get(_loc2_));
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
			this.genericId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.genericId.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
