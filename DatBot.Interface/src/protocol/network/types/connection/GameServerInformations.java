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

	private int id;
	private int type;
	private boolean isMonoAccount;
	private int status;
	private int completion;
	private boolean isSelectable;
	private int charactersCount;
	private int charactersSlots;
	private double date;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };
	public boolean isIsMonoAccount() { return this.isMonoAccount; }
	public void setIsMonoAccount(boolean isMonoAccount) { this.isMonoAccount = isMonoAccount; };
	public int getStatus() { return this.status; }
	public void setStatus(int status) { this.status = status; };
	public int getCompletion() { return this.completion; }
	public void setCompletion(int completion) { this.completion = completion; };
	public boolean isIsSelectable() { return this.isSelectable; }
	public void setIsSelectable(boolean isSelectable) { this.isSelectable = isSelectable; };
	public int getCharactersCount() { return this.charactersCount; }
	public void setCharactersCount(int charactersCount) { this.charactersCount = charactersCount; };
	public int getCharactersSlots() { return this.charactersSlots; }
	public void setCharactersSlots(int charactersSlots) { this.charactersSlots = charactersSlots; };
	public double getDate() { return this.date; }
	public void setDate(double date) { this.date = date; };

	public GameServerInformations(){
	}

	public GameServerInformations(int id, int type, boolean isMonoAccount, int status, int completion, boolean isSelectable, int charactersCount, int charactersSlots, double date){
		this.id = id;
		this.type = type;
		this.isMonoAccount = isMonoAccount;
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
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, isMonoAccount);
			flag = BooleanByteWrapper.SetFlag(1, flag, isSelectable);
			writer.writeByte(flag);
			writer.writeVarShort(this.id);
			writer.writeByte(this.type);
			writer.writeByte(this.status);
			writer.writeByte(this.completion);
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
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.isMonoAccount = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isSelectable = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.id = reader.readVarShort();
			this.type = reader.readByte();
			this.status = reader.readByte();
			this.completion = reader.readByte();
			this.charactersCount = reader.readByte();
			this.charactersSlots = reader.readByte();
			this.date = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
