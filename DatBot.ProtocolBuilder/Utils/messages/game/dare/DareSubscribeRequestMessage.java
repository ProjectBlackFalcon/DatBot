package protocol.network.messages.game.dare;

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
public class DareSubscribeRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6666;

	private double dareId;
	private boolean subscribe;

	public double getDareId() { return this.dareId; }
	public void setDareId(double dareId) { this.dareId = dareId; };
	public boolean isSubscribe() { return this.subscribe; }
	public void setSubscribe(boolean subscribe) { this.subscribe = subscribe; };

	public DareSubscribeRequestMessage(){
	}

	public DareSubscribeRequestMessage(double dareId, boolean subscribe){
		this.dareId = dareId;
		this.subscribe = subscribe;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.dareId);
			writer.writeBoolean(this.subscribe);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareId = reader.readDouble();
			this.subscribe = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
