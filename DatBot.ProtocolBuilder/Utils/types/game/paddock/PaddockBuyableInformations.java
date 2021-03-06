package protocol.network.types.game.paddock;

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
public class PaddockBuyableInformations extends NetworkMessage {
	public static final int ProtocolId = 130;

	private long price;
	private boolean locked;

	public long getPrice() { return this.price; }
	public void setPrice(long price) { this.price = price; };
	public boolean isLocked() { return this.locked; }
	public void setLocked(boolean locked) { this.locked = locked; };

	public PaddockBuyableInformations(){
	}

	public PaddockBuyableInformations(long price, boolean locked){
		this.price = price;
		this.locked = locked;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.price);
			writer.writeBoolean(this.locked);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.price = reader.readVarLong();
			this.locked = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
