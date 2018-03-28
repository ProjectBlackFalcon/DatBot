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
public class ShortcutBarRemoveRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6228;

	private int barType;
	private int slot;

	public int getBarType() { return this.barType; }
	public void setBarType(int barType) { this.barType = barType; };
	public int getSlot() { return this.slot; }
	public void setSlot(int slot) { this.slot = slot; };

	public ShortcutBarRemoveRequestMessage(){
	}

	public ShortcutBarRemoveRequestMessage(int barType, int slot){
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
	}

}
