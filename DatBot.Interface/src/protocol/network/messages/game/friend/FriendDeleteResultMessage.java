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

@SuppressWarnings("unused")
public class FriendDeleteResultMessage extends NetworkMessage {
	public static final int ProtocolId = 5601;

	public boolean success;
	public String name;

	public FriendDeleteResultMessage(){
	}

	public FriendDeleteResultMessage(boolean success, String name){
		this.success = success;
		this.name = name;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.success);
			writer.writeUTF(this.name);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.success = reader.readBoolean();
			this.name = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("success : " + this.success);
		//Network.appendDebug("name : " + this.name);
	//}
}
