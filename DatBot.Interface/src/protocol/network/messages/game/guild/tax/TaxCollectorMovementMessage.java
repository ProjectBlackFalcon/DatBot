package protocol.network.messages.game.guild.tax;

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
import protocol.network.types.game.guild.tax.TaxCollectorBasicInformations;

@SuppressWarnings("unused")
public class TaxCollectorMovementMessage extends NetworkMessage {
	public static final int ProtocolId = 5633;

	public int movementType;
	public TaxCollectorBasicInformations basicInfos;
	public long playerId;
	public String playerName;

	public TaxCollectorMovementMessage(){
	}

	public TaxCollectorMovementMessage(int movementType, TaxCollectorBasicInformations basicInfos, long playerId, String playerName){
		this.movementType = movementType;
		this.basicInfos = basicInfos;
		this.playerId = playerId;
		this.playerName = playerName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.movementType);
			basicInfos.Serialize(writer);
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.movementType = reader.readByte();
			this.basicInfos = new TaxCollectorBasicInformations();
			this.basicInfos.Deserialize(reader);
			this.playerId = reader.readVarLong();
			this.playerName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("movementType : " + this.movementType);
		//Network.appendDebug("basicInfos : " + this.basicInfos);
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("playerName : " + this.playerName);
	//}
}