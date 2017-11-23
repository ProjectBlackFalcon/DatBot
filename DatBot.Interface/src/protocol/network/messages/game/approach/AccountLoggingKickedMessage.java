package protocol.network.messages.game.approach;

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
public class AccountLoggingKickedMessage extends NetworkMessage {
	public static final int ProtocolId = 6029;

	public int days;
	public int hours;
	public int minutes;

	public AccountLoggingKickedMessage(){
	}

	public AccountLoggingKickedMessage(int days, int hours, int minutes){
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.days);
			writer.writeByte(this.hours);
			writer.writeByte(this.minutes);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.days = reader.readVarShort();
			this.hours = reader.readByte();
			this.minutes = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("days : " + this.days);
		//Network.appendDebug("hours : " + this.hours);
		//Network.appendDebug("minutes : " + this.minutes);
	//}
}
