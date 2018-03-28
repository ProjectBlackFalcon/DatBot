package protocol.network.messages.game.prism;

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
public class PrismFightSwapRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5901;

	private int subAreaId;
	private long targetId;

	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public long getTargetId() { return this.targetId; }
	public void setTargetId(long targetId) { this.targetId = targetId; };

	public PrismFightSwapRequestMessage(){
	}

	public PrismFightSwapRequestMessage(int subAreaId, long targetId){
		this.subAreaId = subAreaId;
		this.targetId = targetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeVarLong(this.targetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.targetId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
