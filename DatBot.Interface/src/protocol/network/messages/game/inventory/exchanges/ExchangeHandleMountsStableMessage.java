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
public class ExchangeHandleMountsStableMessage extends NetworkMessage {
	public static final int ProtocolId = 6562;

	public int actionType;
	public List<Integer> ridesId;

	public ExchangeHandleMountsStableMessage(){
	}

	public ExchangeHandleMountsStableMessage(int actionType, List<Integer> ridesId){
		this.actionType = actionType;
		this.ridesId = ridesId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.actionType);
			writer.writeShort(this.ridesId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ridesId.size()){
				writer.writeVarInt(this.ridesId.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.actionType = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.ridesId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.ridesId.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("actionType : " + this.actionType);
		//for(Integer a : ridesId) {
			//Network.appendDebug("ridesId : " + a);
		//}
	//}
}
