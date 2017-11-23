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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ExchangeMultiCraftCrafterCanUseHisRessourcesMessage extends NetworkMessage {
	public static final int ProtocolId = 6020;

	public boolean allowed;

	public ExchangeMultiCraftCrafterCanUseHisRessourcesMessage(){
	}

	public ExchangeMultiCraftCrafterCanUseHisRessourcesMessage(boolean allowed){
		this.allowed = allowed;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.allowed);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allowed = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("allowed : " + this.allowed);
	//}
}
