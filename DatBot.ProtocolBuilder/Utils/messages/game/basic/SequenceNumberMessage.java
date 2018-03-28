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
public class SequenceNumberMessage extends NetworkMessage {
	public static final int ProtocolId = 6317;

	private int number;

	public int getNumber() { return this.number; }
	public void setNumber(int number) { this.number = number; };

	public SequenceNumberMessage(){
	}

	public SequenceNumberMessage(int number){
		this.number = number;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.number);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.number = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
