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
import protocol.network.types.game.shortcut.Shortcut;

@SuppressWarnings("unused")
public class ShortcutBarAddRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6225;

	private int barType;
	private Shortcut shortcut;

	public int getBarType() { return this.barType; }
	public void setBarType(int barType) { this.barType = barType; };
	public Shortcut getShortcut() { return this.shortcut; }
	public void setShortcut(Shortcut shortcut) { this.shortcut = shortcut; };

	public ShortcutBarAddRequestMessage(){
	}

	public ShortcutBarAddRequestMessage(int barType, Shortcut shortcut){
		this.barType = barType;
		this.shortcut = shortcut;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.barType);
			writer.writeShort(Shortcut.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.barType = reader.readByte();
			this.shortcut = (Shortcut) ProtocolTypeManager.getInstance(reader.readShort());
			this.shortcut.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
