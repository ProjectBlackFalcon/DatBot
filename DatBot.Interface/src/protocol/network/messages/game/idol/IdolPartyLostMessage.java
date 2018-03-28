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

@SuppressWarnings("unused")
public class IdolPartyLostMessage extends NetworkMessage {
	public static final int ProtocolId = 6580;

	private int idolId;

	public int getIdolId() { return this.idolId; }
	public void setIdolId(int idolId) { this.idolId = idolId; };

	public IdolPartyLostMessage(){
	}

	public IdolPartyLostMessage(int idolId){
		this.idolId = idolId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.idolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.idolId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
