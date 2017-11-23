package protocol.network.messages.game.tinsel;

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
public class OrnamentSelectedMessage extends NetworkMessage {
	public static final int ProtocolId = 6369;

	public int ornamentId;

	public OrnamentSelectedMessage(){
	}

	public OrnamentSelectedMessage(int ornamentId){
		this.ornamentId = ornamentId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.ornamentId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.ornamentId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("ornamentId : " + this.ornamentId);
	//}
}
