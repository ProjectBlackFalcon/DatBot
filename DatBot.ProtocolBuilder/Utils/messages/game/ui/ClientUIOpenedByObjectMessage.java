package protocol.network.messages.game.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.ui.ClientUIOpenedMessage;

@SuppressWarnings("unused")
public class ClientUIOpenedByObjectMessage extends ClientUIOpenedMessage {
	public static final int ProtocolId = 6463;

	private int uid;

	public int getUid() { return this.uid; };
	public void setUid(int uid) { this.uid = uid; };

	public ClientUIOpenedByObjectMessage(){
	}

	public ClientUIOpenedByObjectMessage(int uid){
		this.uid = uid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.uid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.uid = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
