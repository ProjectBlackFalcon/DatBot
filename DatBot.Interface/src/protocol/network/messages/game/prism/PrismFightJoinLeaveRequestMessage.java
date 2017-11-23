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
public class PrismFightJoinLeaveRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5843;

	public int subAreaId;
	public boolean join;

	public PrismFightJoinLeaveRequestMessage(){
	}

	public PrismFightJoinLeaveRequestMessage(int subAreaId, boolean join){
		this.subAreaId = subAreaId;
		this.join = join;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeBoolean(this.join);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.join = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("join : " + this.join);
	//}
}
