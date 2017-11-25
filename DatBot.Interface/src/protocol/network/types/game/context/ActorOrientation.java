package protocol.network.types.game.context;

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
public class ActorOrientation extends NetworkMessage {
	public static final int ProtocolId = 353;

	public double id;
	public int direction;

	public ActorOrientation(){
	}

	public ActorOrientation(double id, int direction){
		this.id = id;
		this.direction = direction;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.id);
			writer.writeByte(this.direction);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readDouble();
			this.direction = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("direction : " + this.direction);
	//}
}