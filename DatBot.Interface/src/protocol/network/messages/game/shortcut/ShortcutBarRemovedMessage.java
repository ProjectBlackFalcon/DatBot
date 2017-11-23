package protocol.network.messages.game.shortcut;

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
public class ShortcutBarRemovedMessage extends NetworkMessage {
	public static final int ProtocolId = 6224;

	public int barType;
	public int slot;

	public ShortcutBarRemovedMessage(){
	}

	public ShortcutBarRemovedMessage(int barType, int slot){
		this.barType = barType;
		this.slot = slot;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.barType);
			writer.writeByte(this.slot);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.barType = reader.readByte();
			this.slot = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("barType : " + this.barType);
		//Network.appendDebug("slot : " + this.slot);
	//}
}
