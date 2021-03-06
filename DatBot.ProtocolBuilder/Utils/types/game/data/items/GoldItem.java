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
import protocol.network.types.game.data.items.Item;

@SuppressWarnings("unused")
public class GoldItem extends Item {
	public static final int ProtocolId = 123;

	private long sum;

	public long getSum() { return this.sum; }
	public void setSum(long sum) { this.sum = sum; };

	public GoldItem(){
	}

	public GoldItem(long sum){
		this.sum = sum;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.sum);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.sum = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
