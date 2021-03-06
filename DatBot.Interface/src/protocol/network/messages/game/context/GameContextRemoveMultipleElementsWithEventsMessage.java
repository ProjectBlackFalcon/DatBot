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
import protocol.network.messages.game.context.GameContextRemoveMultipleElementsMessage;

@SuppressWarnings("unused")
public class GameContextRemoveMultipleElementsWithEventsMessage extends GameContextRemoveMultipleElementsMessage {
	public static final int ProtocolId = 6416;

	private List<Integer> elementEventIds;

	public List<Integer> getElementEventIds() { return this.elementEventIds; }
	public void setElementEventIds(List<Integer> elementEventIds) { this.elementEventIds = elementEventIds; };

	public GameContextRemoveMultipleElementsWithEventsMessage(){
	}

	public GameContextRemoveMultipleElementsWithEventsMessage(List<Integer> elementEventIds){
		this.elementEventIds = elementEventIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.elementEventIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.elementEventIds.size()){
				writer.writeByte(this.elementEventIds.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.elementEventIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.elementEventIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
