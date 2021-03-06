package protocol.network.messages.game.context.mount;

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
public class MountHarnessColorsUpdateRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6697;

	private boolean useHarnessColors;

	public boolean isUseHarnessColors() { return this.useHarnessColors; }
	public void setUseHarnessColors(boolean useHarnessColors) { this.useHarnessColors = useHarnessColors; };

	public MountHarnessColorsUpdateRequestMessage(){
	}

	public MountHarnessColorsUpdateRequestMessage(boolean useHarnessColors){
		this.useHarnessColors = useHarnessColors;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.useHarnessColors);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.useHarnessColors = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
