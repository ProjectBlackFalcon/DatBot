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
public class PaddockSellRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5953;

	private long price;
	private boolean forSale;

	public long getPrice() { return this.price; };
	public void setPrice(long price) { this.price = price; };
	public boolean isForSale() { return this.forSale; };
	public void setForSale(boolean forSale) { this.forSale = forSale; };

	public PaddockSellRequestMessage(){
	}

	public PaddockSellRequestMessage(long price, boolean forSale){
		this.price = price;
		this.forSale = forSale;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.price);
			writer.writeBoolean(this.forSale);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.price = reader.readVarLong();
			this.forSale = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
