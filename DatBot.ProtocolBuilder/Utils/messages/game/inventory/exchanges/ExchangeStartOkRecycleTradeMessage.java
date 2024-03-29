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
public class ExchangeStartOkRecycleTradeMessage extends NetworkMessage {
	public static final int ProtocolId = 6600;

	private int percentToPrism;
	private int percentToPlayer;

	public int getPercentToPrism() { return this.percentToPrism; }
	public void setPercentToPrism(int percentToPrism) { this.percentToPrism = percentToPrism; };
	public int getPercentToPlayer() { return this.percentToPlayer; }
	public void setPercentToPlayer(int percentToPlayer) { this.percentToPlayer = percentToPlayer; };

	public ExchangeStartOkRecycleTradeMessage(){
	}

	public ExchangeStartOkRecycleTradeMessage(int percentToPrism, int percentToPlayer){
		this.percentToPrism = percentToPrism;
		this.percentToPlayer = percentToPlayer;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.percentToPrism);
			writer.writeShort(this.percentToPlayer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.percentToPrism = reader.readShort();
			this.percentToPlayer = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
