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
public class DareWonMessage extends NetworkMessage {
	public static final int ProtocolId = 6681;

	private double dareId;

	public double getDareId() { return this.dareId; }
	public void setDareId(double dareId) { this.dareId = dareId; };

	public DareWonMessage(){
	}

	public DareWonMessage(double dareId){
		this.dareId = dareId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.dareId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
