package protocol.network.messages.game.inventory.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMessage;
import protocol.network.types.game.data.items.ObjectItem;

@SuppressWarnings("unused")
public class ExchangeObjectPutInBagMessage extends ExchangeObjectMessage {
	public static final int ProtocolId = 6009;

	private ObjectItem object;

	public ObjectItem getObject() { return this.object; };
	public void setObject(ObjectItem object) { this.object = object; };

	public ExchangeObjectPutInBagMessage(){
	}

	public ExchangeObjectPutInBagMessage(ObjectItem object){
		this.object = object;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			object.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.object = new ObjectItem();
			this.object.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
