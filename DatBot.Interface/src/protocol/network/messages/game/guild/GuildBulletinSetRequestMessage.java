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
public class GuildBulletinSetRequestMessage extends SocialNoticeSetRequestMessage {
	public static final int ProtocolId = 6694;

	public String content;
	public boolean notifyMembers;

	public GuildBulletinSetRequestMessage(){
	}

	public GuildBulletinSetRequestMessage(String content, boolean notifyMembers){
		this.content = content;
		this.notifyMembers = notifyMembers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.content);
			writer.writeBoolean(this.notifyMembers);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.content = reader.readUTF();
			this.notifyMembers = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("content : " + this.content);
		//Network.appendDebug("notifyMembers : " + this.notifyMembers);
	//}
}
