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
import protocol.network.types.game.interactive.InteractiveElement;

@SuppressWarnings("unused")
public class InteractiveMapUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5002;

	public List<InteractiveElement> interactiveElements;

	public InteractiveMapUpdateMessage(){
	}

	public InteractiveMapUpdateMessage(List<InteractiveElement> interactiveElements){
		this.interactiveElements = interactiveElements;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.interactiveElements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.interactiveElements.size()){
				writer.writeShort(InteractiveElement.ProtocolId);
				this.interactiveElements.get(_loc2_).Serialize(writer);
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
			this.interactiveElements = new ArrayList<InteractiveElement>();
			while( _loc3_ <  _loc2_){
				InteractiveElement _loc15_ = (InteractiveElement) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.interactiveElements.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(InteractiveElement a : interactiveElements) {
			//Network.appendDebug("interactiveElements : " + a);
		//}
	//}
}
