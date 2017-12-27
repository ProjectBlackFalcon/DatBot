package protocol.network.messages.game.inventory;

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
public class KamasUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5537;

	private long kamasTotal;

	public long getKamasTotal() { return this.kamasTotal; };
	public void setKamasTotal(long kamasTotal) { this.kamasTotal = kamasTotal; };

	public KamasUpdateMessage(){
	}

	public KamasUpdateMessage(long kamasTotal){
		this.kamasTotal = kamasTotal;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.kamasTotal);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.kamasTotal = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
