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

	private boolean buyOrSell;
	private double purchasableId;
	private int purchasableInstanceId;
	private boolean secondHand;
	private long price;

	public boolean isBuyOrSell() { return this.buyOrSell; }
	public void setBuyOrSell(boolean buyOrSell) { this.buyOrSell = buyOrSell; };
	public double getPurchasableId() { return this.purchasableId; }
	public void setPurchasableId(double purchasableId) { this.purchasableId = purchasableId; };
	public int getPurchasableInstanceId() { return this.purchasableInstanceId; }
	public void setPurchasableInstanceId(int purchasableInstanceId) { this.purchasableInstanceId = purchasableInstanceId; };
	public boolean isSecondHand() { return this.secondHand; }
	public void setSecondHand(boolean secondHand) { this.secondHand = secondHand; };
	public long getPrice() { return this.price; }
	public void setPrice(long price) { this.price = price; };

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
	}

}
