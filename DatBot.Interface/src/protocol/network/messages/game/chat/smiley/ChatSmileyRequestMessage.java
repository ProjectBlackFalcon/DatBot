package protocol.network.messages.game.chat.smiley;

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
public class ChatSmileyRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 800;

	private int smileyId;

	public int getSmileyId() { return this.smileyId; }
	public void setSmileyId(int smileyId) { this.smileyId = smileyId; };

	public ChatSmileyRequestMessage(){
	}

	public ChatSmileyRequestMessage(int smileyId){
		this.smileyId = smileyId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.smileyId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.smileyId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
