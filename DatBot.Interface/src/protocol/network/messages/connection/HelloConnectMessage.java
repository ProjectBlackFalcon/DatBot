package protocol.network.messages.connection;

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
public class HelloConnectMessage extends NetworkMessage {
	public static final int ProtocolId = 3;

	private String salt;
	private List<Integer> key;

	public String getSalt() { return this.salt; }
	public void setSalt(String salt) { this.salt = salt; };
	public List<Integer> getKey() { return this.key; }
	public void setKey(List<Integer> key) { this.key = key; };

	public HelloConnectMessage(){
	}

	public HelloConnectMessage(String salt, List<Integer> key){
		this.salt = salt;
		this.key = key;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.salt);
			writer.writeVarInt(this.key.size());
			int _loc2_ = 0;
			while( _loc2_ < this.key.size()){
				writer.writeByte(this.key.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.salt = reader.readUTF();
			int _loc2_  = reader.readVarInt();
			int _loc3_  = 0;
			this.key = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.key.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
