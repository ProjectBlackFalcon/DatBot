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
import protocol.network.messages.game.inventory.exchanges.ExchangeCraftResultMessage;

@SuppressWarnings("unused")
public class ExchangeCraftResultWithObjectIdMessage extends ExchangeCraftResultMessage {
	public static final int ProtocolId = 6000;

	private int objectGenericId;

	public int getObjectGenericId() { return this.objectGenericId; }
	public void setObjectGenericId(int objectGenericId) { this.objectGenericId = objectGenericId; };

	public ExchangeCraftResultWithObjectIdMessage(){
	}

	public ExchangeCraftResultWithObjectIdMessage(int objectGenericId){
		this.objectGenericId = objectGenericId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.objectGenericId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectGenericId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
