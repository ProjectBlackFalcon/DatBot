package protocol.network.messages.common.basic;

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
public class BasicPingMessage extends NetworkMessage {
	public static final int ProtocolId = 182;

	private boolean quiet;

	public boolean isQuiet() { return this.quiet; }
	public void setQuiet(boolean quiet) { this.quiet = quiet; };

	public BasicPingMessage(){
	}

	public BasicPingMessage(boolean quiet){
		this.quiet = quiet;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.quiet);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.quiet = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
