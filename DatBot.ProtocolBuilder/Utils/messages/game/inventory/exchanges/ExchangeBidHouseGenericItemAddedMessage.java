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
public class ExchangeBidHouseGenericItemAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 5947;

	private int objGenericId;

	public int getObjGenericId() { return this.objGenericId; };
	public void setObjGenericId(int objGenericId) { this.objGenericId = objGenericId; };

	public ExchangeBidHouseGenericItemAddedMessage(){
	}

	public ExchangeBidHouseGenericItemAddedMessage(int objGenericId){
		this.objGenericId = objGenericId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.objGenericId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objGenericId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
