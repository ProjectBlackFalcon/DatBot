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
import protocol.network.types.game.character.CharacterMinimalInformations;

@SuppressWarnings("unused")
public class BreachInvitationOfferMessage extends NetworkMessage {
	public static final int ProtocolId = 6805;

	private CharacterMinimalInformations host;
	private int timeLeftBeforeCancel;

	public CharacterMinimalInformations getHost() { return this.host; }
	public void setHost(CharacterMinimalInformations host) { this.host = host; };
	public int getTimeLeftBeforeCancel() { return this.timeLeftBeforeCancel; }
	public void setTimeLeftBeforeCancel(int timeLeftBeforeCancel) { this.timeLeftBeforeCancel = timeLeftBeforeCancel; };

	public BreachInvitationOfferMessage(){
	}

	public BreachInvitationOfferMessage(CharacterMinimalInformations host, int timeLeftBeforeCancel){
		this.host = host;
		this.timeLeftBeforeCancel = timeLeftBeforeCancel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			host.Serialize(writer);
			writer.writeVarInt(this.timeLeftBeforeCancel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.host = new CharacterMinimalInformations();
			this.host.Deserialize(reader);
			this.timeLeftBeforeCancel = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
