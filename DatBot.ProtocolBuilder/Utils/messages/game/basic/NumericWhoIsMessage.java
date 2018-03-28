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
public class NumericWhoIsMessage extends NetworkMessage {
	public static final int ProtocolId = 6297;

	private long playerId;
	private int accountId;

	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public int getAccountId() { return this.accountId; }
	public void setAccountId(int accountId) { this.accountId = accountId; };

	public NumericWhoIsMessage(){
	}

	public NumericWhoIsMessage(long playerId, int accountId){
		this.playerId = playerId;
		this.accountId = accountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.playerId);
			writer.writeInt(this.accountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerId = reader.readVarLong();
			this.accountId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
