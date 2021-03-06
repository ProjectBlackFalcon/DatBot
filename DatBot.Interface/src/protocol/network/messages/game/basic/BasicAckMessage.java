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
public class BasicAckMessage extends NetworkMessage {
	public static final int ProtocolId = 6362;

	private int seq;
	private int lastPacketId;

	public int getSeq() { return this.seq; }
	public void setSeq(int seq) { this.seq = seq; };
	public int getLastPacketId() { return this.lastPacketId; }
	public void setLastPacketId(int lastPacketId) { this.lastPacketId = lastPacketId; };

	public BasicAckMessage(){
	}

	public BasicAckMessage(int seq, int lastPacketId){
		this.seq = seq;
		this.lastPacketId = lastPacketId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.seq);
			writer.writeVarShort(this.lastPacketId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.seq = reader.readVarInt();
			this.lastPacketId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
