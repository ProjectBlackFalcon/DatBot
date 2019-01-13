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
public class BreachSavedMessage extends NetworkMessage {
	public static final int ProtocolId = 6798;

	private boolean saved;

	public boolean isSaved() { return this.saved; }
	public void setSaved(boolean saved) { this.saved = saved; };

	public BreachSavedMessage(){
	}

	public BreachSavedMessage(boolean saved){
		this.saved = saved;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.saved);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.saved = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
