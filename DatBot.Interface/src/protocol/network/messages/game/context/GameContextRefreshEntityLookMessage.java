package protocol.network.messages.game.context;

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
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class GameContextRefreshEntityLookMessage extends NetworkMessage {
	public static final int ProtocolId = 5637;

	public double id;
	public EntityLook look;

	public GameContextRefreshEntityLookMessage(){
	}

	public GameContextRefreshEntityLookMessage(double id, EntityLook look){
		this.id = id;
		this.look = look;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.id);
			look.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readDouble();
			this.look = new EntityLook();
			this.look.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("look : " + this.look);
	//}
}
