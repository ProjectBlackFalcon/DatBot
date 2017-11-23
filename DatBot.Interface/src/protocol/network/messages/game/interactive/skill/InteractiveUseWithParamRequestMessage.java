package protocol.network.messages.game.interactive.skill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.interactive.InteractiveUseRequestMessage;

@SuppressWarnings("unused")
public class InteractiveUseWithParamRequestMessage extends InteractiveUseRequestMessage {
	public static final int ProtocolId = 6715;

	public int id;

	public InteractiveUseWithParamRequestMessage(){
	}

	public InteractiveUseWithParamRequestMessage(int id){
		this.id = id;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.id = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
	//}
}
