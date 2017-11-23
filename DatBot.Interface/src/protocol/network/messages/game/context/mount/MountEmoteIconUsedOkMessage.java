package protocol.network.messages.game.context.mount;

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
public class MountEmoteIconUsedOkMessage extends NetworkMessage {
	public static final int ProtocolId = 5978;

	public int mountId;
	public int reactionType;

	public MountEmoteIconUsedOkMessage(){
	}

	public MountEmoteIconUsedOkMessage(int mountId, int reactionType){
		this.mountId = mountId;
		this.reactionType = reactionType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.mountId);
			writer.writeByte(this.reactionType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mountId = reader.readVarInt();
			this.reactionType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("mountId : " + this.mountId);
		//Network.appendDebug("reactionType : " + this.reactionType);
	//}
}
