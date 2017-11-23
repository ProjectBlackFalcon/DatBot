package protocol.network.messages.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.CurrentMapMessage;

@SuppressWarnings("unused")
public class CurrentMapInstanceMessage extends CurrentMapMessage {
	public static final int ProtocolId = 6738;

	public double instantiatedMapId;

	public CurrentMapInstanceMessage(){
	}

	public CurrentMapInstanceMessage(double instantiatedMapId){
		this.instantiatedMapId = instantiatedMapId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.instantiatedMapId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.instantiatedMapId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("instantiatedMapId : " + this.instantiatedMapId);
	//}
}
