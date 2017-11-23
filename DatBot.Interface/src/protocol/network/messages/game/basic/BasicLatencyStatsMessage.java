package protocol.network.messages.game.basic;

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
public class BasicLatencyStatsMessage extends NetworkMessage {
	public static final int ProtocolId = 5663;

	public int latency;
	public int sampleCount;
	public int max;

	public BasicLatencyStatsMessage(){
	}

	public BasicLatencyStatsMessage(int latency, int sampleCount, int max){
		this.latency = latency;
		this.sampleCount = sampleCount;
		this.max = max;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.latency);
			writer.writeVarShort(this.sampleCount);
			writer.writeVarShort(this.max);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.latency = reader.readShort();
			this.sampleCount = reader.readVarShort();
			this.max = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("latency : " + this.latency);
		//Network.appendDebug("sampleCount : " + this.sampleCount);
		//Network.appendDebug("max : " + this.max);
	//}
}
