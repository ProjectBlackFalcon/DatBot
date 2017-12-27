package protocol.network.messages.game.context;

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
import protocol.network.types.game.context.IdentifiedEntityDispositionInformations;

@SuppressWarnings("unused")
public class GameEntityDispositionMessage extends NetworkMessage {
	public static final int ProtocolId = 5693;

	private IdentifiedEntityDispositionInformations disposition;

	public IdentifiedEntityDispositionInformations getDisposition() { return this.disposition; };
	public void setDisposition(IdentifiedEntityDispositionInformations disposition) { this.disposition = disposition; };

	public GameEntityDispositionMessage(){
	}

	public GameEntityDispositionMessage(IdentifiedEntityDispositionInformations disposition){
		this.disposition = disposition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			disposition.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.disposition = new IdentifiedEntityDispositionInformations();
			this.disposition.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
