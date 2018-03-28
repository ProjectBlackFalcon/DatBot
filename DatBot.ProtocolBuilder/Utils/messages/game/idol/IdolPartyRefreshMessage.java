package protocol.network.messages.game.idol;

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
import protocol.network.types.game.idol.PartyIdol;

@SuppressWarnings("unused")
public class IdolPartyRefreshMessage extends NetworkMessage {
	public static final int ProtocolId = 6583;

	private PartyIdol partyIdol;

	public PartyIdol getPartyIdol() { return this.partyIdol; }
	public void setPartyIdol(PartyIdol partyIdol) { this.partyIdol = partyIdol; };

	public IdolPartyRefreshMessage(){
	}

	public IdolPartyRefreshMessage(PartyIdol partyIdol){
		this.partyIdol = partyIdol;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			partyIdol.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.partyIdol = new PartyIdol();
			this.partyIdol.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
