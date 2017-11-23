package protocol.network.messages.security;

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
public class CheckIntegrityMessage extends NetworkMessage {
	public static final int ProtocolId = 6372;

	public List<Integer> data;

	public CheckIntegrityMessage(){
	}

	public CheckIntegrityMessage(List<Integer> data){
		this.data = data;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.data.size());
			int _loc2_ = 0;
			while( _loc2_ < this.data.size()){
				writer.writeByte(this.data.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readVarInt();
			int _loc3_  = 0;
			this.data = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.data.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : data) {
			//Network.appendDebug("data : " + a);
		//}
	//}
}
