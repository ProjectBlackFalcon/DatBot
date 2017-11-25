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
import protocol.network.messages.game.chat.smiley.ChatSmileyMessage;

@SuppressWarnings("unused")
public class LocalizedChatSmileyMessage extends ChatSmileyMessage {
	public static final int ProtocolId = 6185;

	public int cellId;

	public LocalizedChatSmileyMessage(){
	}

	public LocalizedChatSmileyMessage(int cellId){
		this.cellId = cellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.cellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.cellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("cellId : " + this.cellId);
	//}
}