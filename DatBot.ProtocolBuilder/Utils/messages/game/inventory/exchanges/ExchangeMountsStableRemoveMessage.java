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
public class ExchangeMountsStableRemoveMessage extends NetworkMessage {
	public static final int ProtocolId = 6556;

	private List<Integer> mountsId;

	public List<Integer> getMountsId() { return this.mountsId; };
	public void setMountsId(List<Integer> mountsId) { this.mountsId = mountsId; };

	public ExchangeMountsStableRemoveMessage(){
	}

	public ExchangeMountsStableRemoveMessage(List<Integer> mountsId){
		this.mountsId = mountsId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.mountsId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.mountsId.size()){
				writer.writeVarInt(this.mountsId.get(_loc2_));
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
			this.mountsId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.mountsId.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
