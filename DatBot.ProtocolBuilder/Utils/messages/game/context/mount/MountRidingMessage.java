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

	private boolean isRiding;
	private boolean isAutopilot;

	public boolean isIsRiding() { return this.isRiding; }
	public void setIsRiding(boolean isRiding) { this.isRiding = isRiding; };
	public boolean isIsAutopilot() { return this.isAutopilot; }
	public void setIsAutopilot(boolean isAutopilot) { this.isAutopilot = isAutopilot; };

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
	}

}
