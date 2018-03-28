package protocol.network.messages.game.context.roleplay.emote;

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
public class EmotePlayRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5685;

	private int emoteId;

	public int getEmoteId() { return this.emoteId; }
	public void setEmoteId(int emoteId) { this.emoteId = emoteId; };

	public EmotePlayRequestMessage(){
	}

	public EmotePlayRequestMessage(int emoteId){
		this.emoteId = emoteId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.emoteId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.emoteId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
