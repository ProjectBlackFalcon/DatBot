package protocol.network.messages.common.basic;

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
public class BasicStatMessage extends NetworkMessage {
	public static final int ProtocolId = 6530;

	private double timeSpent;
	private int statId;

	public double getTimeSpent() { return this.timeSpent; }
	public void setTimeSpent(double timeSpent) { this.timeSpent = timeSpent; };
	public int getStatId() { return this.statId; }
	public void setStatId(int statId) { this.statId = statId; };

	public BasicStatMessage(){
	}

	public BasicStatMessage(double timeSpent, int statId){
		this.timeSpent = timeSpent;
		this.statId = statId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.timeSpent);
			writer.writeVarShort(this.statId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.timeSpent = reader.readDouble();
			this.statId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
