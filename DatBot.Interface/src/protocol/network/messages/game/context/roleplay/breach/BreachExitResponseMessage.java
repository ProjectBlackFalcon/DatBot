package protocol.network.messages.game.context.roleplay.breach;

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
public class BreachExitResponseMessage extends NetworkMessage {
	public static final int ProtocolId = 6814;

	private boolean exited;

	public boolean isExited() { return this.exited; }
	public void setExited(boolean exited) { this.exited = exited; };

	public BreachExitResponseMessage(){
	}

	public BreachExitResponseMessage(boolean exited){
		this.exited = exited;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.exited);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.exited = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
