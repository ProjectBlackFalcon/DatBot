package protocol.network.messages.game.friend;

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
import protocol.network.types.game.friend.IgnoredInformations;

@SuppressWarnings("unused")
public class IgnoredListMessage extends NetworkMessage {
	public static final int ProtocolId = 5674;

	private List<IgnoredInformations> ignoredList;

	public List<IgnoredInformations> getIgnoredList() { return this.ignoredList; }
	public void setIgnoredList(List<IgnoredInformations> ignoredList) { this.ignoredList = ignoredList; };

	public IgnoredListMessage(){
	}

	public IgnoredListMessage(List<IgnoredInformations> ignoredList){
		this.ignoredList = ignoredList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.ignoredList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ignoredList.size()){
				writer.writeShort(IgnoredInformations.ProtocolId);
				this.ignoredList.get(_loc2_).Serialize(writer);
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
			this.ignoredList = new ArrayList<IgnoredInformations>();
			while( _loc3_ <  _loc2_){
				IgnoredInformations _loc15_ = (IgnoredInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.ignoredList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
