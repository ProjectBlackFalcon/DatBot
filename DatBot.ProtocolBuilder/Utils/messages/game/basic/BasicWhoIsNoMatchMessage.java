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
public class BasicWhoIsNoMatchMessage extends NetworkMessage {
	public static final int ProtocolId = 179;

	private String search;

	public String getSearch() { return this.search; };
	public void setSearch(String search) { this.search = search; };

	public BasicWhoIsNoMatchMessage(){
	}

	public BasicWhoIsNoMatchMessage(String search){
		this.search = search;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.search);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.search = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
