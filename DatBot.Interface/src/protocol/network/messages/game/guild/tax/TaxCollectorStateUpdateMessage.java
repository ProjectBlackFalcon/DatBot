package protocol.network.messages.game.guild.tax;

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
public class TaxCollectorStateUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6455;

	public double uniqueId;
	public int state;

	public TaxCollectorStateUpdateMessage(){
	}

	public TaxCollectorStateUpdateMessage(double uniqueId, int state){
		this.uniqueId = uniqueId;
		this.state = state;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.uniqueId);
			writer.writeByte(this.state);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.uniqueId = reader.readDouble();
			this.state = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("uniqueId : " + this.uniqueId);
		//Network.appendDebug("state : " + this.state);
	//}
}
