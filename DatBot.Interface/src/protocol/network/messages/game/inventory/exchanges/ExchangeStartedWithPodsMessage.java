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
import protocol.network.messages.game.inventory.exchanges.ExchangeStartedMessage;

@SuppressWarnings("unused")
public class ExchangeStartedWithPodsMessage extends ExchangeStartedMessage {
	public static final int ProtocolId = 6129;

	public double firstCharacterId;
	public int firstCharacterCurrentWeight;
	public int firstCharacterMaxWeight;
	public double secondCharacterId;
	public int secondCharacterCurrentWeight;
	public int secondCharacterMaxWeight;

	public ExchangeStartedWithPodsMessage(){
	}

	public ExchangeStartedWithPodsMessage(double firstCharacterId, int firstCharacterCurrentWeight, int firstCharacterMaxWeight, double secondCharacterId, int secondCharacterCurrentWeight, int secondCharacterMaxWeight){
		this.firstCharacterId = firstCharacterId;
		this.firstCharacterCurrentWeight = firstCharacterCurrentWeight;
		this.firstCharacterMaxWeight = firstCharacterMaxWeight;
		this.secondCharacterId = secondCharacterId;
		this.secondCharacterCurrentWeight = secondCharacterCurrentWeight;
		this.secondCharacterMaxWeight = secondCharacterMaxWeight;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.firstCharacterId);
			writer.writeVarInt(this.firstCharacterCurrentWeight);
			writer.writeVarInt(this.firstCharacterMaxWeight);
			writer.writeDouble(this.secondCharacterId);
			writer.writeVarInt(this.secondCharacterCurrentWeight);
			writer.writeVarInt(this.secondCharacterMaxWeight);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.firstCharacterId = reader.readDouble();
			this.firstCharacterCurrentWeight = reader.readVarInt();
			this.firstCharacterMaxWeight = reader.readVarInt();
			this.secondCharacterId = reader.readDouble();
			this.secondCharacterCurrentWeight = reader.readVarInt();
			this.secondCharacterMaxWeight = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("firstCharacterId : " + this.firstCharacterId);
		//Network.appendDebug("firstCharacterCurrentWeight : " + this.firstCharacterCurrentWeight);
		//Network.appendDebug("firstCharacterMaxWeight : " + this.firstCharacterMaxWeight);
		//Network.appendDebug("secondCharacterId : " + this.secondCharacterId);
		//Network.appendDebug("secondCharacterCurrentWeight : " + this.secondCharacterCurrentWeight);
		//Network.appendDebug("secondCharacterMaxWeight : " + this.secondCharacterMaxWeight);
	//}
}
