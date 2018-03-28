package protocol.network.messages.game.context.roleplay.havenbag.meeting;

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
public class InviteInHavenBagOfferMessage extends NetworkMessage {
	public static final int ProtocolId = 6643;

	private CharacterMinimalInformations hostInformations;
	private int timeLeftBeforeCancel;

	public CharacterMinimalInformations getHostInformations() { return this.hostInformations; }
	public void setHostInformations(CharacterMinimalInformations hostInformations) { this.hostInformations = hostInformations; };
	public int getTimeLeftBeforeCancel() { return this.timeLeftBeforeCancel; }
	public void setTimeLeftBeforeCancel(int timeLeftBeforeCancel) { this.timeLeftBeforeCancel = timeLeftBeforeCancel; };

	public InviteInHavenBagOfferMessage(){
	}

	public InviteInHavenBagOfferMessage(CharacterMinimalInformations hostInformations, int timeLeftBeforeCancel){
		this.hostInformations = hostInformations;
		this.timeLeftBeforeCancel = timeLeftBeforeCancel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			hostInformations.Serialize(writer);
			writer.writeVarInt(this.timeLeftBeforeCancel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.hostInformations = new CharacterMinimalInformations();
			this.hostInformations.Deserialize(reader);
			this.timeLeftBeforeCancel = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
