package protocol.network.types.game.shortcut;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.shortcut.Shortcut;

@SuppressWarnings("unused")
public class ShortcutEmote extends Shortcut {
	public static final int ProtocolId = 389;

	private int emoteId;

	public int getEmoteId() { return this.emoteId; }
	public void setEmoteId(int emoteId) { this.emoteId = emoteId; };

	public ShortcutEmote(){
	}

	public ShortcutEmote(int emoteId){
		this.emoteId = emoteId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.emoteId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.emoteId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
