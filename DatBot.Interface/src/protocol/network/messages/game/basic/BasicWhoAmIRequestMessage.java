package protocol.network.messages.game.basic;

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
public class BasicWhoAmIRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5664;

	private boolean verbose;

	public boolean isVerbose() { return this.verbose; }
	public void setVerbose(boolean verbose) { this.verbose = verbose; };

	public BasicWhoAmIRequestMessage(){
	}

	public BasicWhoAmIRequestMessage(boolean verbose){
		this.verbose = verbose;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.verbose);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.verbose = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
