package protocol.network.messages.game.context.notification;

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
public class NotificationListMessage extends NetworkMessage {
	public static final int ProtocolId = 6087;

	private List<Integer> flags;

	public List<Integer> getFlags() { return this.flags; };
	public void setFlags(List<Integer> flags) { this.flags = flags; };

	public NotificationListMessage(){
	}

	public NotificationListMessage(List<Integer> flags){
		this.flags = flags;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.flags.size());
			int _loc2_ = 0;
			while( _loc2_ < this.flags.size()){
				writer.writeVarInt(this.flags.get(_loc2_));
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
			this.flags = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.flags.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
