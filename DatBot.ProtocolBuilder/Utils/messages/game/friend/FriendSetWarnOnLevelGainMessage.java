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
public class FriendSetWarnOnLevelGainMessage extends NetworkMessage {
	public static final int ProtocolId = 6077;

	private boolean enable;

	public boolean isEnable() { return this.enable; };
	public void setEnable(boolean enable) { this.enable = enable; };

	public FriendSetWarnOnLevelGainMessage(){
	}

	public FriendSetWarnOnLevelGainMessage(boolean enable){
		this.enable = enable;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.enable);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.enable = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
