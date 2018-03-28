package protocol.network.messages.game.alliance;

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
public class AllianceBulletinSetRequestMessage extends SocialNoticeSetRequestMessage {
	public static final int ProtocolId = 6693;

	private String content;
	private boolean notifyMembers;

	public String getContent() { return this.content; }
	public void setContent(String content) { this.content = content; };
	public boolean isNotifyMembers() { return this.notifyMembers; }
	public void setNotifyMembers(boolean notifyMembers) { this.notifyMembers = notifyMembers; };

	public AllianceBulletinSetRequestMessage(){
	}

	public AllianceBulletinSetRequestMessage(String content, boolean notifyMembers){
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
	}

}
