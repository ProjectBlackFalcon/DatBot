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
import protocol.network.messages.game.inventory.exchanges.ExchangeBidPriceMessage;

@SuppressWarnings("unused")
public class ExchangeBidPriceForSellerMessage extends ExchangeBidPriceMessage {
	public static final int ProtocolId = 6464;

	private boolean allIdentical;
	private List<Long> minimalPrices;

	public boolean isAllIdentical() { return this.allIdentical; };
	public void setAllIdentical(boolean allIdentical) { this.allIdentical = allIdentical; };
	public List<Long> getMinimalPrices() { return this.minimalPrices; };
	public void setMinimalPrices(List<Long> minimalPrices) { this.minimalPrices = minimalPrices; };

	public ExchangeBidPriceForSellerMessage(){
	}

	public ExchangeBidPriceForSellerMessage(boolean allIdentical, List<Long> minimalPrices){
		this.allIdentical = allIdentical;
		this.minimalPrices = minimalPrices;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.allIdentical);
			writer.writeShort(this.minimalPrices.size());
			int _loc2_ = 0;
			while( _loc2_ < this.minimalPrices.size()){
				writer.writeVarLong(this.minimalPrices.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allIdentical = reader.readBoolean();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.minimalPrices = new ArrayList<Long>();
			while( _loc3_ <  _loc2_){
				long _loc15_ = reader.readVarLong();
				this.minimalPrices.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
