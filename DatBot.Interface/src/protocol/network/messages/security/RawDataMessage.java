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
public class RawDataMessage extends NetworkMessage {
	public static final int ProtocolId = 6253;

	private List<Integer> content;

	public List<Integer> getContent() { return this.content; }
	public void setContent(List<Integer> content) { this.content = content; };

	public RawDataMessage(){
	}

	public RawDataMessage(List<Integer> content){
		this.content = content;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.content.size());
			int _loc2_ = 0;
			while( _loc2_ < this.content.size()){
				writer.writeByte(this.content.get(_loc2_));
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
			this.content = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.content.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
