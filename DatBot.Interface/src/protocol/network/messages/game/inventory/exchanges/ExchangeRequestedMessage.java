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

@SuppressWarnings("unused")
public class ExchangeRequestedMessage extends NetworkMessage {
	public static final int ProtocolId = 5522;

	private int exchangeType;

	public int getExchangeType() { return this.exchangeType; }
	public void setExchangeType(int exchangeType) { this.exchangeType = exchangeType; };

	public ExchangeRequestedMessage(){
	}

	public ExchangeRequestedMessage(int exchangeType){
		this.exchangeType = exchangeType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.exchangeType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.exchangeType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
