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
public class ExchangeObjectTransfertListWithQuantityToInvMessage extends NetworkMessage {
	public static final int ProtocolId = 6470;

	public List<Integer> ids;
	public List<Integer> qtys;

	public ExchangeObjectTransfertListWithQuantityToInvMessage(){
	}

	public ExchangeObjectTransfertListWithQuantityToInvMessage(List<Integer> ids, List<Integer> qtys){
		this.ids = ids;
		this.qtys = qtys;
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
			writer.writeShort(this.qtys.size());
			int _loc3_ = 0;
			while( _loc3_ < this.qtys.size()){
				writer.writeVarInt(this.qtys.get(_loc3_));
				_loc3_++;
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
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.qtys = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarInt();
				this.qtys.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : ids) {
			//Network.appendDebug("ids : " + a);
		//}
		//for(Integer a : qtys) {
			//Network.appendDebug("qtys : " + a);
		//}
	//}
}
