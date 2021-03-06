package protocol.network.messages.game.chat.channel;

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
public class EnabledChannelsMessage extends NetworkMessage {
	public static final int ProtocolId = 892;

	private List<Integer> channels;
	private List<Integer> disallowed;

	public List<Integer> getChannels() { return this.channels; }
	public void setChannels(List<Integer> channels) { this.channels = channels; };
	public List<Integer> getDisallowed() { return this.disallowed; }
	public void setDisallowed(List<Integer> disallowed) { this.disallowed = disallowed; };

	public EnabledChannelsMessage(){
	}

	public EnabledChannelsMessage(List<Integer> channels, List<Integer> disallowed){
		this.channels = channels;
		this.disallowed = disallowed;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.channels.size());
			int _loc2_ = 0;
			while( _loc2_ < this.channels.size()){
				writer.writeByte(this.channels.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.disallowed.size());
			int _loc3_ = 0;
			while( _loc3_ < this.disallowed.size()){
				writer.writeByte(this.disallowed.get(_loc3_));
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
			this.channels = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.channels.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.disallowed = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readByte();
				this.disallowed.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
