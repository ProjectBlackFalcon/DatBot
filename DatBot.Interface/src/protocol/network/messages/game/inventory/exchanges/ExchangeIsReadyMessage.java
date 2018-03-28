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
public class ExchangeIsReadyMessage extends NetworkMessage {
	public static final int ProtocolId = 5509;

	private double id;
	private boolean ready;

	public double getId() { return this.id; }
	public void setId(double id) { this.id = id; };
	public boolean isReady() { return this.ready; }
	public void setReady(boolean ready) { this.ready = ready; };

	public ExchangeIsReadyMessage(){
	}

	public ExchangeIsReadyMessage(double id, boolean ready){
		this.id = id;
		this.ready = ready;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.id);
			writer.writeBoolean(this.ready);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readDouble();
			this.ready = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
