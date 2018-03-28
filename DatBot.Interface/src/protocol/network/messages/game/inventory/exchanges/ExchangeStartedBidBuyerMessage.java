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
import protocol.network.NetworkMessage;
import protocol.network.types.game.data.items.SellerBuyerDescriptor;

@SuppressWarnings("unused")
public class ExchangeStartedBidBuyerMessage extends NetworkMessage {
	public static final int ProtocolId = 5904;

	private SellerBuyerDescriptor buyerDescriptor;

	public SellerBuyerDescriptor getBuyerDescriptor() { return this.buyerDescriptor; }
	public void setBuyerDescriptor(SellerBuyerDescriptor buyerDescriptor) { this.buyerDescriptor = buyerDescriptor; };

	public ExchangeStartedBidBuyerMessage(){
	}

	public ExchangeStartedBidBuyerMessage(SellerBuyerDescriptor buyerDescriptor){
		this.buyerDescriptor = buyerDescriptor;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			buyerDescriptor.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.buyerDescriptor = new SellerBuyerDescriptor();
			this.buyerDescriptor.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
