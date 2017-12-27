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
public class ShortcutBarSwapErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 6226;

	private int error;

	public int getError() { return this.error; };
	public void setError(int error) { this.error = error; };

	public ShortcutBarSwapErrorMessage(){
	}

	public ShortcutBarSwapErrorMessage(int error){
		this.error = error;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.error);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.error = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
