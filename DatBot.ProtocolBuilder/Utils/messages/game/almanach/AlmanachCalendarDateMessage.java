package protocol.network.messages.game.almanach;

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
public class AlmanachCalendarDateMessage extends NetworkMessage {
	public static final int ProtocolId = 6341;

	private int date;

	public int getDate() { return this.date; };
	public void setDate(int date) { this.date = date; };

	public AlmanachCalendarDateMessage(){
	}

	public AlmanachCalendarDateMessage(int date){
		this.date = date;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.date);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.date = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
