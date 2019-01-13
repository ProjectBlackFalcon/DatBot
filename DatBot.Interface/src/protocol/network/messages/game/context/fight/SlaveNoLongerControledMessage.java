package protocol.network.messages.game.context.fight;

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
public class SlaveNoLongerControledMessage extends NetworkMessage {
	public static final int ProtocolId = 6807;

	private double masterId;
	private double slaveId;

	public double getMasterId() { return this.masterId; }
	public void setMasterId(double masterId) { this.masterId = masterId; };
	public double getSlaveId() { return this.slaveId; }
	public void setSlaveId(double slaveId) { this.slaveId = slaveId; };

	public SlaveNoLongerControledMessage(){
	}

	public SlaveNoLongerControledMessage(double masterId, double slaveId){
		this.masterId = masterId;
		this.slaveId = slaveId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.masterId);
			writer.writeDouble(this.slaveId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.masterId = reader.readDouble();
			this.slaveId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
