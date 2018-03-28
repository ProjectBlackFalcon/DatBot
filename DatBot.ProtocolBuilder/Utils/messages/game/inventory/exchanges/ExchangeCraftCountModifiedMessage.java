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
public class ExchangeCraftCountModifiedMessage extends NetworkMessage {
	public static final int ProtocolId = 6595;

	private int count;

	public int getCount() { return this.count; }
	public void setCount(int count) { this.count = count; };

	public ExchangeCraftCountModifiedMessage(){
	}

	public ExchangeCraftCountModifiedMessage(int count){
		this.count = count;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.count);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.count = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
