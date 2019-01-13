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
public class BreachTeleportResponseMessage extends NetworkMessage {
	public static final int ProtocolId = 6816;

	private boolean teleported;

	public boolean isTeleported() { return this.teleported; }
	public void setTeleported(boolean teleported) { this.teleported = teleported; };

	public BreachTeleportResponseMessage(){
	}

	public BreachTeleportResponseMessage(boolean teleported){
		this.teleported = teleported;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.teleported);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.teleported = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
