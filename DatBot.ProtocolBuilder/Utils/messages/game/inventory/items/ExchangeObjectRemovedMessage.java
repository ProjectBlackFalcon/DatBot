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

@SuppressWarnings("unused")
public class ExchangeObjectRemovedMessage extends ExchangeObjectMessage {
	public static final int ProtocolId = 5517;

	private int objectUID;

	public int getObjectUID() { return this.objectUID; };
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };

	public ExchangeObjectRemovedMessage(){
	}

	public ExchangeObjectRemovedMessage(int objectUID){
		this.objectUID = objectUID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.objectUID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectUID = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
