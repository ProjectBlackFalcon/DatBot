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
public class AllianceInvitationAnswerMessage extends NetworkMessage {
	public static final int ProtocolId = 6401;

	private boolean accept;

	public boolean isAccept() { return this.accept; }
	public void setAccept(boolean accept) { this.accept = accept; };

	public AllianceInvitationAnswerMessage(){
	}

	public AllianceInvitationAnswerMessage(boolean accept){
		this.accept = accept;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.accept);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.accept = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
