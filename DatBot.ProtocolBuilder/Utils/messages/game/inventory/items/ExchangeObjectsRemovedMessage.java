package protocol.network.messages.game.inventory.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMessage;

@SuppressWarnings("unused")
public class ExchangeObjectsRemovedMessage extends ExchangeObjectMessage {
	public static final int ProtocolId = 6532;

	private List<Integer> objectUID;

	public List<Integer> getObjectUID() { return this.objectUID; }
	public void setObjectUID(List<Integer> objectUID) { this.objectUID = objectUID; };

	public ExchangeObjectsRemovedMessage(){
	}

	public ExchangeObjectsRemovedMessage(List<Integer> objectUID){
		this.objectUID = objectUID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.objectUID.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectUID.size()){
				writer.writeVarInt(this.objectUID.get(_loc2_));
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
			this.objectUID = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.objectUID.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
