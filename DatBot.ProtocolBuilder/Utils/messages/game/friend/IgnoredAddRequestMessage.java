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
public class IgnoredAddRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5673;

	private String name;
	private boolean session;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };
	public boolean isSession() { return this.session; }
	public void setSession(boolean session) { this.session = session; };

	public IgnoredAddRequestMessage(){
	}

	public IgnoredAddRequestMessage(String name, boolean session){
		this.name = name;
		this.session = session;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.name);
			writer.writeBoolean(this.session);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.name = reader.readUTF();
			this.session = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
