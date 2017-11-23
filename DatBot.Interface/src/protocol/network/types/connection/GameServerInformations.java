package protocol.network.types.connection;

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
public class GameServerInformations extends NetworkMessage {
	public static final int ProtocolId = 25;

	public int id;
	public int type;
	public int status;
	public int completion;
	public boolean isSelectable;
	public int charactersCount;
	public int charactersSlots;
	public double date;

	public GameServerInformations(){
	}

	public GameServerInformations(int id, int type, int status, int completion, boolean isSelectable, int charactersCount, int charactersSlots, double date){
		this.id = id;
		this.type = type;
		this.status = status;
		this.completion = completion;
		this.isSelectable = isSelectable;
		this.charactersCount = charactersCount;
		this.charactersSlots = charactersSlots;
		this.date = date;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeByte(this.type);
			writer.writeByte(this.status);
			writer.writeByte(this.completion);
			writer.writeBoolean(this.isSelectable);
			writer.writeByte(this.charactersCount);
			writer.writeByte(this.charactersSlots);
			writer.writeDouble(this.date);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			this.type = reader.readByte();
			this.status = reader.readByte();
			this.completion = reader.readByte();
			this.isSelectable = reader.readBoolean();
			this.charactersCount = reader.readByte();
			this.charactersSlots = reader.readByte();
			this.date = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("type : " + this.type);
		//Network.appendDebug("status : " + this.status);
		//Network.appendDebug("completion : " + this.completion);
		//Network.appendDebug("isSelectable : " + this.isSelectable);
		//Network.appendDebug("charactersCount : " + this.charactersCount);
		//Network.appendDebug("charactersSlots : " + this.charactersSlots);
		//Network.appendDebug("date : " + this.date);
	//}
}
