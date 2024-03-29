package protocol.network.messages.updater.parts;

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
import protocol.network.types.updater.ContentPart;

@SuppressWarnings("unused")
public class PartsListMessage extends NetworkMessage {
	public static final int ProtocolId = 1502;

	public List<ContentPart> parts;

	public PartsListMessage(){
	}

	public PartsListMessage(List<ContentPart> parts){
		this.parts = parts;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.parts.size());
			int _loc2_ = 0;
			while( _loc2_ < this.parts.size()){
				this.parts.get(_loc2_).Serialize(writer);
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
			this.parts = new ArrayList<ContentPart>();
			while( _loc3_ <  _loc2_){
				ContentPart _loc15_ = new ContentPart();
				_loc15_.Deserialize(reader);
				this.parts.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(ContentPart a : parts) {
			//Network.appendDebug("parts : " + a);
		//}
	//}
}
