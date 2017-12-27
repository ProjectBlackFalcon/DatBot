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
public class ExchangeObjectTransfertListFromInvMessage extends NetworkMessage {
	public static final int ProtocolId = 6183;

	private List<Integer> ids;

	public List<Integer> getIds() { return this.ids; };
	public void setIds(List<Integer> ids) { this.ids = ids; };

	public ExchangeObjectTransfertListFromInvMessage(){
	}

	public ExchangeObjectTransfertListFromInvMessage(List<Integer> ids){
		this.ids = ids;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.ids.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ids.size()){
				writer.writeVarInt(this.ids.get(_loc2_));
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
			this.ids = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.ids.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
