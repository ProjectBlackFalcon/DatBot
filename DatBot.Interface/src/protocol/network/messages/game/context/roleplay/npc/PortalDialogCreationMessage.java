package protocol.network.messages.game.context.roleplay.npc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.npc.NpcDialogCreationMessage;

@SuppressWarnings("unused")
public class PortalDialogCreationMessage extends NpcDialogCreationMessage {
	public static final int ProtocolId = 6737;

	private int type;

	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };

	public PortalDialogCreationMessage(){
	}

	public PortalDialogCreationMessage(int type){
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.type);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.type = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
