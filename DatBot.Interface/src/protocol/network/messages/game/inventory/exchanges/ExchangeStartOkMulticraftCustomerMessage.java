package protocol.network.messages.game.inventory.exchanges;

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
public class ExchangeStartOkMulticraftCustomerMessage extends NetworkMessage {
	public static final int ProtocolId = 5817;

	public int skillId;
	public int crafterJobLevel;

	public ExchangeStartOkMulticraftCustomerMessage(){
	}

	public ExchangeStartOkMulticraftCustomerMessage(int skillId, int crafterJobLevel){
		this.skillId = skillId;
		this.crafterJobLevel = crafterJobLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.skillId);
			writer.writeByte(this.crafterJobLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.skillId = reader.readVarInt();
			this.crafterJobLevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("skillId : " + this.skillId);
		//Network.appendDebug("crafterJobLevel : " + this.crafterJobLevel);
	//}
}
