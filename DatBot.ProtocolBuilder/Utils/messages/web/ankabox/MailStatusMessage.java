package protocol.network.messages.web.ankabox;

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
public class MailStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6275;

	private int unread;
	private int total;

	public int getUnread() { return this.unread; }
	public void setUnread(int unread) { this.unread = unread; };
	public int getTotal() { return this.total; }
	public void setTotal(int total) { this.total = total; };

	public MailStatusMessage(){
	}

	public MailStatusMessage(int unread, int total){
		this.unread = unread;
		this.total = total;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.unread);
			writer.writeVarShort(this.total);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.unread = reader.readVarShort();
			this.total = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
