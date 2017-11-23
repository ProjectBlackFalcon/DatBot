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
import protocol.network.messages.game.inventory.items.ObtainedItemMessage;

@SuppressWarnings("unused")
public class ObtainedItemWithBonusMessage extends ObtainedItemMessage {
	public static final int ProtocolId = 6520;

	public int bonusQuantity;

	public ObtainedItemWithBonusMessage(){
	}

	public ObtainedItemWithBonusMessage(int bonusQuantity){
		this.bonusQuantity = bonusQuantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.bonusQuantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.bonusQuantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("bonusQuantity : " + this.bonusQuantity);
	//}
}
