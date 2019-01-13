package protocol.network.messages.game.context.roleplay.breach.meeting;

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
public class BreachInvitationRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6794;

	private long guest;

	public long getGuest() { return this.guest; }
	public void setGuest(long guest) { this.guest = guest; };

	public BreachInvitationRequestMessage(){
	}

	public BreachInvitationRequestMessage(long guest){
		this.guest = guest;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.guest);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guest = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
