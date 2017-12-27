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
import protocol.network.types.game.data.items.ObjectItemNotInContainer;

@SuppressWarnings("unused")
public class ExchangeCraftResultWithObjectDescMessage extends ExchangeCraftResultMessage {
	public static final int ProtocolId = 5999;

	private ObjectItemNotInContainer objectInfo;

	public ObjectItemNotInContainer getObjectInfo() { return this.objectInfo; };
	public void setObjectInfo(ObjectItemNotInContainer objectInfo) { this.objectInfo = objectInfo; };

	public ExchangeCraftResultWithObjectDescMessage(){
	}

	public ExchangeCraftResultWithObjectDescMessage(ObjectItemNotInContainer objectInfo){
		this.objectInfo = objectInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			objectInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectInfo = new ObjectItemNotInContainer();
			this.objectInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
