package protocol.network.messages.game.startup;

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
public class StartupActionFinishedMessage extends NetworkMessage {
	public static final int ProtocolId = 1304;

	public boolean success;
	public int actionId;
	public boolean automaticAction;

	public StartupActionFinishedMessage(){
	}

	public StartupActionFinishedMessage(boolean success, int actionId, boolean automaticAction){
		this.success = success;
		this.actionId = actionId;
		this.automaticAction = automaticAction;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, success);
			flag = BooleanByteWrapper.SetFlag(1, flag, automaticAction);
			writer.writeByte(flag);
			writer.writeInt(this.actionId);
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
			this.automaticAction = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.actionId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("success : " + this.success);
		//Network.appendDebug("actionId : " + this.actionId);
		//Network.appendDebug("automaticAction : " + this.automaticAction);
	//}
}
