package protocol.network.types.game.data.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.ObjectItemGenericQuantity;

@SuppressWarnings("unused")
public class ObjectItemGenericQuantityPrice extends ObjectItemGenericQuantity {
	public static final int ProtocolId = 494;

	private long price;

	public long getPrice() { return this.price; }
	public void setPrice(long price) { this.price = price; };

	public ObjectItemGenericQuantityPrice(){
	}

	public ObjectItemGenericQuantityPrice(long price){
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
