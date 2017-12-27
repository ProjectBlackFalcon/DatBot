package protocol.network.messages.game.guild;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.social.SocialNoticeSetRequestMessage;

@SuppressWarnings("unused")
public class GuildMotdSetRequestMessage extends SocialNoticeSetRequestMessage {
	public static final int ProtocolId = 6588;

	private String content;

	public String getContent() { return this.content; };
	public void setContent(String content) { this.content = content; };

	public GuildMotdSetRequestMessage(){
	}

	public GuildMotdSetRequestMessage(String content){
		this.content = content;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.content);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.content = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
