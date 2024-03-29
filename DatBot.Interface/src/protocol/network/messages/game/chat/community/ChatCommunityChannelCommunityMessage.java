package protocol.network.messages.game.chat.community;

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
public class ChatCommunityChannelCommunityMessage extends NetworkMessage {
	public static final int ProtocolId = 6730;

	private int communityId;

	public int getCommunityId() { return this.communityId; }
	public void setCommunityId(int communityId) { this.communityId = communityId; };

	public ChatCommunityChannelCommunityMessage(){
	}

	public ChatCommunityChannelCommunityMessage(int communityId){
		this.communityId = communityId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.communityId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.communityId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
