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
public class FriendSetStatusShareMessage extends NetworkMessage {
	public static final int ProtocolId = 6822;

	private boolean share;

	public boolean isShare() { return this.share; }
	public void setShare(boolean share) { this.share = share; };

	public FriendSetStatusShareMessage(){
	}

	public FriendSetStatusShareMessage(boolean share){
		this.share = share;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.share);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.share = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
