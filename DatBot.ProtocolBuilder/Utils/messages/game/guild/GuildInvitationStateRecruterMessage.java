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
public class GuildInvitationStateRecruterMessage extends NetworkMessage {
	public static final int ProtocolId = 5563;

	private String recrutedName;
	private int invitationState;

	public String getRecrutedName() { return this.recrutedName; };
	public void setRecrutedName(String recrutedName) { this.recrutedName = recrutedName; };
	public int getInvitationState() { return this.invitationState; };
	public void setInvitationState(int invitationState) { this.invitationState = invitationState; };

	public GuildInvitationStateRecruterMessage(){
	}

	public GuildInvitationStateRecruterMessage(String recrutedName, int invitationState){
		this.recrutedName = recrutedName;
		this.invitationState = invitationState;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.recrutedName);
			writer.writeByte(this.invitationState);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.recrutedName = reader.readUTF();
			this.invitationState = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
