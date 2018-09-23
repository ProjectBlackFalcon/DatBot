package protocol.network.types.game.inventory.exchanges;

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
public class RecycledItem extends NetworkMessage {
	public static final int ProtocolId = 547;

	private int id;
	private int qty;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public int getQty() { return this.qty; }
	public void setQty(int qty) { this.qty = qty; };

	public RecycledItem(){
	}

	public RecycledItem(int id, int qty){
		this.id = id;
		this.qty = qty;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeUnsignedInt(this.qty);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			this.qty = reader.readUnsignedByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
