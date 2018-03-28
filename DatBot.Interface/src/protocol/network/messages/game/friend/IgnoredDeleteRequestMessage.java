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
public class IgnoredDeleteRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5680;

	private int accountId;
	private boolean session;

	public int getAccountId() { return this.accountId; }
	public void setAccountId(int accountId) { this.accountId = accountId; };
	public boolean isSession() { return this.session; }
	public void setSession(boolean session) { this.session = session; };

	public IgnoredDeleteRequestMessage(){
	}

	public IgnoredDeleteRequestMessage(int accountId, boolean session){
		this.accountId = accountId;
		this.session = session;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.accountId);
			writer.writeBoolean(this.session);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.accountId = reader.readInt();
			this.session = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
