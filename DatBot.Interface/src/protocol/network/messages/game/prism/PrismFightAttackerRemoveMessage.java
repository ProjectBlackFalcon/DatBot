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
public class PrismFightAttackerRemoveMessage extends NetworkMessage {
	public static final int ProtocolId = 5897;

	public int subAreaId;
	public int fightId;
	public long fighterToRemoveId;

	public PrismFightAttackerRemoveMessage(){
	}

	public PrismFightAttackerRemoveMessage(int subAreaId, int fightId, long fighterToRemoveId){
		this.subAreaId = subAreaId;
		this.fightId = fightId;
		this.fighterToRemoveId = fighterToRemoveId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeVarShort(this.fightId);
			writer.writeVarLong(this.fighterToRemoveId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.fightId = reader.readVarShort();
			this.fighterToRemoveId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("fighterToRemoveId : " + this.fighterToRemoveId);
	//}
}
