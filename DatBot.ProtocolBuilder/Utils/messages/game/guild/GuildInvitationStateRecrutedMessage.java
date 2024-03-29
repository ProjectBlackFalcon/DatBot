package protocol.network.messages.game.guild;

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
public class GuildInvitationStateRecrutedMessage extends NetworkMessage {
	public static final int ProtocolId = 5548;

	private int invitationState;

	public int getInvitationState() { return this.invitationState; }
	public void setInvitationState(int invitationState) { this.invitationState = invitationState; };

	public GuildInvitationStateRecrutedMessage(){
	}

	public GuildInvitationStateRecrutedMessage(int invitationState){
		this.invitationState = invitationState;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.invitationState);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.invitationState = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
