package protocol.network.messages.game.inventory.exchanges;

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
public class ExchangeObjectMessage extends NetworkMessage {
	public static final int ProtocolId = 5515;

	private boolean remote;

	public boolean isRemote() { return this.remote; };
	public void setRemote(boolean remote) { this.remote = remote; };

	public ExchangeObjectMessage(){
	}

	public ExchangeObjectMessage(boolean remote){
		this.remote = remote;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.remote);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.remote = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
