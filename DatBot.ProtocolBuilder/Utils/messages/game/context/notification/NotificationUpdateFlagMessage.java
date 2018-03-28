package protocol.network.messages.game.context.notification;

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
public class NotificationUpdateFlagMessage extends NetworkMessage {
	public static final int ProtocolId = 6090;

	private int index;

	public int getIndex() { return this.index; }
	public void setIndex(int index) { this.index = index; };

	public NotificationUpdateFlagMessage(){
	}

	public NotificationUpdateFlagMessage(int index){
		this.index = index;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.index);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.index = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
