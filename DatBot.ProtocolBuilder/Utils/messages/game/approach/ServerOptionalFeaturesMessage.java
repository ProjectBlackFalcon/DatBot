package protocol.network.messages.game.approach;

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
public class ServerOptionalFeaturesMessage extends NetworkMessage {
	public static final int ProtocolId = 6305;

	private List<Integer> features;

	public List<Integer> getFeatures() { return this.features; };
	public void setFeatures(List<Integer> features) { this.features = features; };

	public ServerOptionalFeaturesMessage(){
	}

	public ServerOptionalFeaturesMessage(List<Integer> features){
		this.features = features;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.features.size());
			int _loc2_ = 0;
			while( _loc2_ < this.features.size()){
				writer.writeByte(this.features.get(_loc2_));
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
			this.features = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.features.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
