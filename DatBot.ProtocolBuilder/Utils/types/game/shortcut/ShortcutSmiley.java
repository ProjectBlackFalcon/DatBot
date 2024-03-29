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
public class ShortcutSmiley extends Shortcut {
	public static final int ProtocolId = 388;

	private int smileyId;

	public int getSmileyId() { return this.smileyId; }
	public void setSmileyId(int smileyId) { this.smileyId = smileyId; };

	public ShortcutSmiley(){
	}

	public ShortcutSmiley(int smileyId){
		this.smileyId = smileyId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.smileyId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.smileyId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
