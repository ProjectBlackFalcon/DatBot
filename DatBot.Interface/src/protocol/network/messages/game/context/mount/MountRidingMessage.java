package protocol.network.messages.game.context.mount;

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
public class MountRidingMessage extends NetworkMessage {
	public static final int ProtocolId = 5967;

	public boolean isRiding;
	public boolean isAutopilot;

	public MountRidingMessage(){
	}

	public MountRidingMessage(boolean isRiding, boolean isAutopilot){
		this.isRiding = isRiding;
		this.isAutopilot = isAutopilot;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, isRiding);
			flag = BooleanByteWrapper.SetFlag(1, flag, isAutopilot);
			writer.writeByte(flag);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.isRiding = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isAutopilot = BooleanByteWrapper.GetFlag(flag, (byte) 1);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("isRiding : " + this.isRiding);
		//Network.appendDebug("isAutopilot : " + this.isAutopilot);
	//}
}
