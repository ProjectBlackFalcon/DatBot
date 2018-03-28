package protocol.network.messages.game.context.mount;

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
public class PaddockBuyRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5951;

	private long proposedPrice;

	public long getProposedPrice() { return this.proposedPrice; }
	public void setProposedPrice(long proposedPrice) { this.proposedPrice = proposedPrice; };

	public PaddockBuyRequestMessage(){
	}

	public PaddockBuyRequestMessage(long proposedPrice){
		this.proposedPrice = proposedPrice;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.proposedPrice);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.proposedPrice = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
