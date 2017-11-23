package protocol.network.messages.game.context.roleplay.paddock;

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
public class PaddockToSellFilterMessage extends NetworkMessage {
	public static final int ProtocolId = 6161;

	public int areaId;
	public int atLeastNbMount;
	public int atLeastNbMachine;
	public long maxPrice;

	public PaddockToSellFilterMessage(){
	}

	public PaddockToSellFilterMessage(int areaId, int atLeastNbMount, int atLeastNbMachine, long maxPrice){
		this.areaId = areaId;
		this.atLeastNbMount = atLeastNbMount;
		this.atLeastNbMachine = atLeastNbMachine;
		this.maxPrice = maxPrice;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.areaId);
			writer.writeByte(this.atLeastNbMount);
			writer.writeByte(this.atLeastNbMachine);
			writer.writeVarLong(this.maxPrice);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.areaId = reader.readInt();
			this.atLeastNbMount = reader.readByte();
			this.atLeastNbMachine = reader.readByte();
			this.maxPrice = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("areaId : " + this.areaId);
		//Network.appendDebug("atLeastNbMount : " + this.atLeastNbMount);
		//Network.appendDebug("atLeastNbMachine : " + this.atLeastNbMachine);
		//Network.appendDebug("maxPrice : " + this.maxPrice);
	//}
}
