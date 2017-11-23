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
public class IgnoredDeleteResultMessage extends NetworkMessage {
	public static final int ProtocolId = 5677;

	public boolean success;
	public String name;
	public boolean session;

	public IgnoredDeleteResultMessage(){
	}

	public IgnoredDeleteResultMessage(boolean success, String name, boolean session){
		this.success = success;
		this.name = name;
		this.session = session;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, success);
			flag = BooleanByteWrapper.SetFlag(1, flag, session);
			writer.writeByte(flag);
			writer.writeUTF(this.name);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.success = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.session = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.name = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("success : " + this.success);
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("session : " + this.session);
	//}
}
