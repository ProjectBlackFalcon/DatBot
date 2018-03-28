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
import protocol.network.types.game.friend.IgnoredInformations;

@SuppressWarnings("unused")
public class IgnoredAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 5678;

	private IgnoredInformations ignoreAdded;
	private boolean session;

	public IgnoredInformations getIgnoreAdded() { return this.ignoreAdded; }
	public void setIgnoreAdded(IgnoredInformations ignoreAdded) { this.ignoreAdded = ignoreAdded; };
	public boolean isSession() { return this.session; }
	public void setSession(boolean session) { this.session = session; };

	public IgnoredAddedMessage(){
	}

	public IgnoredAddedMessage(IgnoredInformations ignoreAdded, boolean session){
		this.ignoreAdded = ignoreAdded;
		this.session = session;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(IgnoredInformations.ProtocolId);
			writer.writeBoolean(this.session);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.ignoreAdded = (IgnoredInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.ignoreAdded.Deserialize(reader);
			this.session = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
