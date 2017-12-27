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
import protocol.network.types.game.data.items.ObjectItemMinimalInformation;

@SuppressWarnings("unused")
public class ObjectItemInformationWithQuantity extends ObjectItemMinimalInformation {
	public static final int ProtocolId = 387;

	private int quantity;

	public int getQuantity() { return this.quantity; };
	public void setQuantity(int quantity) { this.quantity = quantity; };

	public ObjectItemInformationWithQuantity(){
	}

	public ObjectItemInformationWithQuantity(int quantity){
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
