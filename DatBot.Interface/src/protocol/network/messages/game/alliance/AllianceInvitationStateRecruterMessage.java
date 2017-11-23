package protocol.network.messages.game.alliance;

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
public class AllianceInvitationStateRecruterMessage extends NetworkMessage {
	public static final int ProtocolId = 6396;

	public String recrutedName;
	public int invitationState;

	public AllianceInvitationStateRecruterMessage(){
	}

	public AllianceInvitationStateRecruterMessage(String recrutedName, int invitationState){
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
		//append();
	}

	//private void append(){
		//Network.appendDebug("recrutedName : " + this.recrutedName);
		//Network.appendDebug("invitationState : " + this.invitationState);
	//}
}
