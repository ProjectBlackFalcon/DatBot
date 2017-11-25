package protocol.network.messages.game.actions.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.actions.AbstractGameActionMessage;

@SuppressWarnings("unused")
public class GameActionFightActivateGlyphTrapMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 6545;

	public int markId;
	public boolean active;

	public GameActionFightActivateGlyphTrapMessage(){
	}

	public GameActionFightActivateGlyphTrapMessage(int markId, boolean active){
		this.markId = markId;
		this.active = active;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.markId);
			writer.writeBoolean(this.active);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.markId = reader.readShort();
			this.active = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("markId : " + this.markId);
		//Network.appendDebug("active : " + this.active);
	//}
}