package protocol.network.messages.game.prism;

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
public class PrismFightStateUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6040;

	private int state;

	public int getState() { return this.state; }
	public void setState(int state) { this.state = state; };

	public PrismFightStateUpdateMessage(){
	}

	public PrismFightStateUpdateMessage(int state){
		this.state = state;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.state);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.state = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
