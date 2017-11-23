package protocol.network.messages.game.basic;

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
public class TextInformationMessage extends NetworkMessage {
	public static final int ProtocolId = 780;

	public int msgType;
	public int msgId;
	public List<String> parameters;

	public TextInformationMessage(){
	}

	public TextInformationMessage(int msgType, int msgId, List<String> parameters){
		this.msgType = msgType;
		this.msgId = msgId;
		this.parameters = parameters;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.msgType);
			writer.writeVarShort(this.msgId);
			writer.writeShort(this.parameters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.parameters.size()){
				writer.writeUTF(this.parameters.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.msgType = reader.readByte();
			this.msgId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.parameters = new ArrayList<String>();
			while( _loc3_ <  _loc2_){
				String _loc15_ = reader.readUTF();
				this.parameters.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("msgType : " + this.msgType);
		//Network.appendDebug("msgId : " + this.msgId);
		//for(String a : parameters) {
			//Network.appendDebug("parameters : " + a);
		//}
	//}
}
