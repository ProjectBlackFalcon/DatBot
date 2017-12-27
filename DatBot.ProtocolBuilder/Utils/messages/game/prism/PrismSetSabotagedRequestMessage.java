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
public class PrismSetSabotagedRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6468;

	private int subAreaId;

	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };

	public PrismSetSabotagedRequestMessage(){
	}

	public PrismSetSabotagedRequestMessage(int subAreaId){
		this.subAreaId = subAreaId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
