package protocol.network.messages.game.context.roleplay.job;

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
public class JobAllowMultiCraftRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5748;

	private boolean enabled;

	public boolean isEnabled() { return this.enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; };

	public JobAllowMultiCraftRequestMessage(){
	}

	public JobAllowMultiCraftRequestMessage(boolean enabled){
		this.enabled = enabled;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.enabled);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.enabled = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
