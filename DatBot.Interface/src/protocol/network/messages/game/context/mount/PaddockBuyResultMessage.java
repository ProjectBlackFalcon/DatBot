package protocol.network.messages.game.context.mount;

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
public class PaddockBuyResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6516;

	public double paddockId;
	public boolean bought;
	public long realPrice;

	public PaddockBuyResultMessage(){
	}

	public PaddockBuyResultMessage(double paddockId, boolean bought, long realPrice){
		this.paddockId = paddockId;
		this.bought = bought;
		this.realPrice = realPrice;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.paddockId);
			writer.writeBoolean(this.bought);
			writer.writeVarLong(this.realPrice);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.paddockId = reader.readDouble();
			this.bought = reader.readBoolean();
			this.realPrice = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("paddockId : " + this.paddockId);
		//Network.appendDebug("bought : " + this.bought);
		//Network.appendDebug("realPrice : " + this.realPrice);
	//}
}
