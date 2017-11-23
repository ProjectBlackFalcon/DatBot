package protocol.network.messages.game.context;

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
public class GameContextRemoveMultipleElementsMessage extends NetworkMessage {
	public static final int ProtocolId = 252;

	public List<Double> elementsIds;

	public GameContextRemoveMultipleElementsMessage(){
	}

	public GameContextRemoveMultipleElementsMessage(List<Double> elementsIds){
		this.elementsIds = elementsIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.elementsIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.elementsIds.size()){
				writer.writeDouble(this.elementsIds.get(_loc2_));
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
			this.elementsIds = new ArrayList<Double>();
			while( _loc3_ <  _loc2_){
				double _loc15_ = reader.readDouble();
				this.elementsIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Double a : elementsIds) {
			//Network.appendDebug("elementsIds : " + a);
		//}
	//}
}
