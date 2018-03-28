package protocol.network.messages.game.inventory.exchanges;

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
public class ExchangeBidHouseSearchMessage extends NetworkMessage {
	public static final int ProtocolId = 5806;

	private int type;
	private int genId;

	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };
	public int getGenId() { return this.genId; }
	public void setGenId(int genId) { this.genId = genId; };

	public ExchangeBidHouseSearchMessage(){
	}

	public ExchangeBidHouseSearchMessage(int type, int genId){
		this.type = type;
		this.genId = genId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.type);
			writer.writeVarShort(this.genId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readVarInt();
			this.genId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
