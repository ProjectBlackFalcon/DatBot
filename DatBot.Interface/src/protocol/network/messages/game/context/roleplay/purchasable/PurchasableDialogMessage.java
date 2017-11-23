package protocol.network.messages.game.context.roleplay.purchasable;

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
public class PurchasableDialogMessage extends NetworkMessage {
	public static final int ProtocolId = 5739;

	public boolean buyOrSell;
	public double purchasableId;
	public int purchasableInstanceId;
	public boolean secondHand;
	public long price;

	public PurchasableDialogMessage(){
	}

	public PurchasableDialogMessage(boolean buyOrSell, double purchasableId, int purchasableInstanceId, boolean secondHand, long price){
		this.buyOrSell = buyOrSell;
		this.purchasableId = purchasableId;
		this.purchasableInstanceId = purchasableInstanceId;
		this.secondHand = secondHand;
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, buyOrSell);
			flag = BooleanByteWrapper.SetFlag(1, flag, secondHand);
			writer.writeByte(flag);
			writer.writeDouble(this.purchasableId);
			writer.writeInt(this.purchasableInstanceId);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.buyOrSell = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.secondHand = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.purchasableId = reader.readDouble();
			this.purchasableInstanceId = reader.readInt();
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("buyOrSell : " + this.buyOrSell);
		//Network.appendDebug("purchasableId : " + this.purchasableId);
		//Network.appendDebug("purchasableInstanceId : " + this.purchasableInstanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
		//Network.appendDebug("price : " + this.price);
	//}
}
