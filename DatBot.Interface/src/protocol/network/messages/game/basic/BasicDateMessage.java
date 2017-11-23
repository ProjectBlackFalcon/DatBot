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
public class BasicDateMessage extends NetworkMessage {
	public static final int ProtocolId = 177;

	public int day;
	public int month;
	public int year;

	public BasicDateMessage(){
	}

	public BasicDateMessage(int day, int month, int year){
		this.day = day;
		this.month = month;
		this.year = year;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.day);
			writer.writeByte(this.month);
			writer.writeShort(this.year);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.day = reader.readByte();
			this.month = reader.readByte();
			this.year = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("day : " + this.day);
		//Network.appendDebug("month : " + this.month);
		//Network.appendDebug("year : " + this.year);
	//}
}
