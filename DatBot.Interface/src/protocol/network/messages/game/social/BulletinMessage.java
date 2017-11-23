package protocol.network.messages.game.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.social.SocialNoticeMessage;

@SuppressWarnings("unused")
public class BulletinMessage extends SocialNoticeMessage {
	public static final int ProtocolId = 6695;

	public int lastNotifiedTimestamp;

	public BulletinMessage(){
	}

	public BulletinMessage(int lastNotifiedTimestamp){
		this.lastNotifiedTimestamp = lastNotifiedTimestamp;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.lastNotifiedTimestamp);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.lastNotifiedTimestamp = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("lastNotifiedTimestamp : " + this.lastNotifiedTimestamp);
	//}
}
