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

	public long price;
	public boolean locked;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("price : " + this.price);
		//Network.appendDebug("locked : " + this.locked);
	//}
}