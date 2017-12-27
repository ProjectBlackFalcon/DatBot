package protocol.network.messages.game.guild;

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
public class ChallengeFightJoinRefusedMessage extends NetworkMessage {
	public static final int ProtocolId = 5908;

	private long playerId;
	private int reason;

	public long getPlayerId() { return this.playerId; };
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public int getReason() { return this.reason; };
	public void setReason(int reason) { this.reason = reason; };

	public ChallengeFightJoinRefusedMessage(){
	}

	public ChallengeFightJoinRefusedMessage(long playerId, int reason){
		this.playerId = playerId;
		this.reason = reason;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.playerId);
			writer.writeByte(this.reason);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerId = reader.readVarLong();
			this.reason = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
