package protocol.network.messages.game.interactive;

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
import protocol.network.types.game.interactive.StatedElement;

@SuppressWarnings("unused")
public class StatedMapUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5716;

	private List<StatedElement> statedElements;

	public List<StatedElement> getStatedElements() { return this.statedElements; };
	public void setStatedElements(List<StatedElement> statedElements) { this.statedElements = statedElements; };

	public StatedMapUpdateMessage(){
	}

	public StatedMapUpdateMessage(List<StatedElement> statedElements){
		this.statedElements = statedElements;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.statedElements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.statedElements.size()){
				this.statedElements.get(_loc2_).Serialize(writer);
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
			this.statedElements = new ArrayList<StatedElement>();
			while( _loc3_ <  _loc2_){
				StatedElement _loc15_ = new StatedElement();
				_loc15_.Deserialize(reader);
				this.statedElements.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
