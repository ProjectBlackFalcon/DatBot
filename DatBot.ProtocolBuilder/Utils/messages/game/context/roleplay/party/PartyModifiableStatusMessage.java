package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyMessage;

@SuppressWarnings("unused")
public class PartyModifiableStatusMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 6277;

	private boolean enabled;

	public boolean isEnabled() { return this.enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; };

	public PartyModifiableStatusMessage(){
	}

	public PartyModifiableStatusMessage(boolean enabled){
		this.enabled = enabled;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.enabled);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.enabled = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
