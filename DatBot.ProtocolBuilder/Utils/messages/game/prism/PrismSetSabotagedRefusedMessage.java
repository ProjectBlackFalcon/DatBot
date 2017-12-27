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
public class PrismSetSabotagedRefusedMessage extends NetworkMessage {
	public static final int ProtocolId = 6466;

	private int subAreaId;
	private int reason;

	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getReason() { return this.reason; };
	public void setReason(int reason) { this.reason = reason; };

	public PrismSetSabotagedRefusedMessage(){
	}

	public PrismSetSabotagedRefusedMessage(int subAreaId, int reason){
		this.subAreaId = subAreaId;
		this.reason = reason;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeByte(this.reason);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.reason = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
