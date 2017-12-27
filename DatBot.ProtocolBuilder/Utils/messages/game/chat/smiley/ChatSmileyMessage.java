package protocol.network.messages.game.chat.smiley;

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
public class ChatSmileyMessage extends NetworkMessage {
	public static final int ProtocolId = 801;

	private double entityId;
	private int smileyId;
	private int accountId;

	public double getEntityId() { return this.entityId; };
	public void setEntityId(double entityId) { this.entityId = entityId; };
	public int getSmileyId() { return this.smileyId; };
	public void setSmileyId(int smileyId) { this.smileyId = smileyId; };
	public int getAccountId() { return this.accountId; };
	public void setAccountId(int accountId) { this.accountId = accountId; };

	public ChatSmileyMessage(){
	}

	public ChatSmileyMessage(double entityId, int smileyId, int accountId){
		this.entityId = entityId;
		this.smileyId = smileyId;
		this.accountId = accountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.entityId);
			writer.writeVarShort(this.smileyId);
			writer.writeInt(this.accountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.entityId = reader.readDouble();
			this.smileyId = reader.readVarShort();
			this.accountId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
