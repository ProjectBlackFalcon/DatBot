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
public class BasicWhoIsRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 181;

	private boolean verbose;
	private String search;

	public boolean isVerbose() { return this.verbose; }
	public void setVerbose(boolean verbose) { this.verbose = verbose; };
	public String getSearch() { return this.search; }
	public void setSearch(String search) { this.search = search; };

	public BasicWhoIsRequestMessage(){
	}

	public BasicWhoIsRequestMessage(boolean verbose, String search){
		this.verbose = verbose;
		this.search = search;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.verbose);
			writer.writeUTF(this.search);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.verbose = reader.readBoolean();
			this.search = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
