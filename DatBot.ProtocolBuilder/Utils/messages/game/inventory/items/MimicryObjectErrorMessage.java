package protocol.network.messages.game.inventory.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.items.SymbioticObjectErrorMessage;

@SuppressWarnings("unused")
public class MimicryObjectErrorMessage extends SymbioticObjectErrorMessage {
	public static final int ProtocolId = 6461;

	private boolean preview;

	public boolean isPreview() { return this.preview; }
	public void setPreview(boolean preview) { this.preview = preview; };

	public MimicryObjectErrorMessage(){
	}

	public MimicryObjectErrorMessage(boolean preview){
		this.preview = preview;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.preview);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.preview = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
