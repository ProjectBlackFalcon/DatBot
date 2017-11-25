package protocol.network.messages.game.inventory.items;

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
public class LivingObjectMessageRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6066;

	public int msgId;
	public List<String> parameters;
	public int livingObject;

	public LivingObjectMessageRequestMessage(){
	}

	public LivingObjectMessageRequestMessage(int msgId, List<String> parameters, int livingObject){
		this.msgId = msgId;
		this.parameters = parameters;
		this.livingObject = livingObject;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.msgId);
			writer.writeShort(this.parameters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.parameters.size()){
				writer.writeUTF(this.parameters.get(_loc2_));
				_loc2_++;
			}
			writer.writeVarInt(this.livingObject);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.msgId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.parameters = new ArrayList<String>();
			while( _loc3_ <  _loc2_){
				String _loc15_ = reader.readUTF();
				this.parameters.add(_loc15_);
				_loc3_++;
			}
			this.livingObject = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("msgId : " + this.msgId);
		//for(String a : parameters) {
			//Network.appendDebug("parameters : " + a);
		//}
		//Network.appendDebug("livingObject : " + this.livingObject);
	//}
}