package protocol.network.messages.game.context.roleplay.npc;

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
public class NpcDialogReplyMessage extends NetworkMessage {
	public static final int ProtocolId = 5616;

	private int replyId;

	public int getReplyId() { return this.replyId; };
	public void setReplyId(int replyId) { this.replyId = replyId; };

	public NpcDialogReplyMessage(){
	}

	public NpcDialogReplyMessage(int replyId){
		this.replyId = replyId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.replyId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.replyId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
