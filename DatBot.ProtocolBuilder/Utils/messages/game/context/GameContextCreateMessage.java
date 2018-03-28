package protocol.network.messages.game.context;

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
public class GameContextCreateMessage extends NetworkMessage {
	public static final int ProtocolId = 200;

	private int context;

	public int getContext() { return this.context; }
	public void setContext(int context) { this.context = context; };

	public GameContextCreateMessage(){
	}

	public GameContextCreateMessage(int context){
		this.context = context;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.context);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.context = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
