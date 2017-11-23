package protocol.network.messages.game.context.roleplay.paddock;

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
public class PaddockSellBuyDialogMessage extends NetworkMessage {
	public static final int ProtocolId = 6018;

	public boolean bsell;
	public int ownerId;
	public long price;

	public PaddockSellBuyDialogMessage(){
	}

	public PaddockSellBuyDialogMessage(boolean bsell, int ownerId, long price){
		this.bsell = bsell;
		this.ownerId = ownerId;
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.bsell);
			writer.writeVarInt(this.ownerId);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.bsell = reader.readBoolean();
			this.ownerId = reader.readVarInt();
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("bsell : " + this.bsell);
		//Network.appendDebug("ownerId : " + this.ownerId);
		//Network.appendDebug("price : " + this.price);
	//}
}
