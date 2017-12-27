package protocol.network.messages.game.context.roleplay.havenbag;

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
public class KickHavenBagRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6652;

	private long guestId;

	public long getGuestId() { return this.guestId; };
	public void setGuestId(long guestId) { this.guestId = guestId; };

	public KickHavenBagRequestMessage(){
	}

	public KickHavenBagRequestMessage(long guestId){
		this.guestId = guestId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.guestId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guestId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
